package ClientNetworking.GameClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import Constants.NetworkingIdentifiers;
import Input.PlayerInput;
import ServerNetworking.GameServer.ServerHandler;

public class ClientServerOutput extends Thread {

    //Socket
    private DatagramSocket udpSocket;
    private InetAddress serverIP;
    private int serverPort;

    //timeout
    private int numTimeoutChecks;
    private final int maxTimeoutChecks = 10;

    private boolean running;

    public ClientServerOutput(){
        try {
            //Socket
            serverIP = InetAddress.getByName(ServerHandler.serverIP);
            serverPort = ServerHandler.gamePort;
            udpSocket = new DatagramSocket();
            udpSocket.connect(serverIP, serverPort);

        }catch(IOException e){
            System.out.println("SERVER Error creating Output datagram udpSocket or data stream.");
        }
    }

    @Override
    public void run() {
        running = true;

        while(running){
            try {
                switch (ClientConnectionHandler.getState()) {
                    case CONNECTED:
                        sendPlayerInput();
                        break;
                    case CONNECTING:
                        checkTimeout();
                        break;
                    case DISCONNECTED:
                        sendConnectionRequest();
                        ClientConnectionHandler.setState(ConnectionState.CONNECTING);
                        break;
                }
            }catch (IOException e){
                e.printStackTrace();
            }

            try {
                Thread.sleep(ServerHandler.clientTickRate);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    private void sendPlayerInput() throws IOException{
        //Data
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(new PlayerInput());
        oos.flush();
        byte[] serializedObject = baos.toByteArray();

        //add packet identifier to packet byte-array output.
        ByteArrayOutputStream compoundingStream = new ByteArrayOutputStream();
        compoundingStream.write(NetworkingIdentifiers.MOVEMENT_PACKET_IDENTIFIER);
        compoundingStream.write(serializedObject);

        byte[] compoundOutput = compoundingStream.toByteArray();
        udpSocket.send(new DatagramPacket(compoundOutput, compoundOutput.length, serverIP, serverPort));
        baos.reset();
    }

    private void sendConnectionRequest() throws IOException{
        String connectionRequestMessage = "Connection request";

        ByteArrayOutputStream compoundingStream = new ByteArrayOutputStream();
        compoundingStream.write(NetworkingIdentifiers.CONNECT_REQUEST_IDENTIFIER);
        compoundingStream.write(connectionRequestMessage.getBytes());

        byte[] compoundOutput = compoundingStream.toByteArray();
        udpSocket.send(new DatagramPacket(compoundOutput, compoundOutput.length, serverIP, serverPort));

        System.out.println("connection request sent");
    }

    private void checkTimeout(){
        if(numTimeoutChecks > maxTimeoutChecks){
            ClientConnectionHandler.setState(ConnectionState.DISCONNECTED);
            numTimeoutChecks = 0;
        }
        numTimeoutChecks++;
    }

    public void close(){ running = false; }
}
