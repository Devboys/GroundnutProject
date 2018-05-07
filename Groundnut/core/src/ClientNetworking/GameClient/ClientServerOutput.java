package ClientNetworking.GameClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import ClientNetworking.ClientNetworkingHandler;
import ClientNetworking.ConnectionState;
import Constants.NetworkingIdentifiers;
import Core.SimulationHandler;
import Input.PlayerInput;
import ServerNetworking.GameServer.ServerHandler;

public class ClientServerOutput extends Thread {

    //Socket
    private DatagramSocket udpSocket;
    private InetAddress serverIP;
    private int serverPort;

    private boolean running;

    public ClientServerOutput(InetAddress hostIP){
        try {
            //Socket
            serverIP = hostIP;
            serverPort = ServerHandler.serverPort;

            udpSocket = new DatagramSocket();
            udpSocket.connect(serverIP, serverPort);

        }catch(IOException e){
            e.printStackTrace();
            System.out.println("SERVER Error creating Output datagram udpSocket or data stream.");
        }
    }

    @Override
    public void run() {
        running = true;

        while(running){
            try {
                if(ClientNetworkingHandler.getState() == ConnectionState.CONNECTED){
                    sendPlayerInput();
                } else {
                    close();
                    System.out.println("CLIENT: Server handler started in incorrect state");
                }
            }catch (IOException e){
                e.printStackTrace();
            }

            try {
                Thread.sleep(ServerHandler.clientTickRate);
            } catch (InterruptedException e){
                close();
                e.printStackTrace();
            }
        }
    }

    private void sendPlayerInput() throws IOException{
        System.out.println("sending inputs");
        //Data
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(SimulationHandler.getInstance().getPlayerInput());
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

    public void close(){ running = false; }
}
