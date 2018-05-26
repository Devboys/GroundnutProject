package ClientNetworking.GameClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import ClientNetworking.ClientNetworkingManager;
import ClientNetworking.ConnectionState;
import Constants.NetworkingIdentifiers;
import Core.SimulationHandler;
import ServerNetworking.GameServer.ServerHandler;

//TODO: OBJECTOUTPUTSTREAM SHOULD NOT BE REMADE EVERY TIME IT IS USED.

/**Sends Player-input objects, sampled from the SimulationHandler, to the server. Packets are sent in intervals.*/
public class ClientServerOutput extends Thread {

    //Socket
    private DatagramSocket udpSocket;
    private int serverPort;

    private boolean running;

    private ClientNetworkingManager parentHandler;

    ClientServerOutput(ClientNetworkingManager parent){
        parentHandler = parent;

        try {
            serverPort = ServerHandler.serverPort;

            udpSocket = new DatagramSocket();
            udpSocket.connect(parentHandler.getHostIP(), serverPort);

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
                if(parentHandler.getState() == ConnectionState.CONNECTED){ //only work if
                    sendPlayerInput();
                } else {
                    System.out.println("CLIENT: Server handler started in incorrect state");
                    parentHandler.setState(ConnectionState.DISCONNECTED);
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
        //Data
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(SimulationHandler.getInstance().getPlayerInput());
        oos.flush();
        oos.close();
        byte[] serializedObject = baos.toByteArray();
        baos.flush();

        //add packet identifier to packet byte-array output such that packet is [identifier][data].
        ByteArrayOutputStream compoundingStream = new ByteArrayOutputStream();
        compoundingStream.write(NetworkingIdentifiers.MOVEMENT_PACKET_IDENTIFIER);
        compoundingStream.write(serializedObject);

        //send compound packet-data to server.
        byte[] compoundOutput = compoundingStream.toByteArray();
        udpSocket.send(
                new DatagramPacket(compoundOutput, compoundOutput.length, parentHandler.getHostIP(), serverPort)
        );
        baos.reset();
        System.out.println("Input sent to: " + parentHandler.getHostIP().toString() + "(" + serverPort + ")");
    }

    /**Closes the output-socket and stops the thread. */
    public void close(){
        running = false;
        udpSocket.close();
    }
}
