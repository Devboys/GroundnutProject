package ClientNetworking.GameClient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;

import Constants.NetworkingIdentifiers;
import ServerNetworking.GameServer.ServerHandler;
import ServerNetworking.GameServer.ServerOutput;

public class ClientInputThread extends Thread {

    //Data
    private final int BUFFER_LENGTH = 256;
    private byte[] buffer = new byte[BUFFER_LENGTH];

    //multicast Socket
    private MulticastSocket udpMulticastSocket;
    private DatagramPacket dgram;

    //connection socket
    private DatagramSocket connectionSocket;

    private boolean running;

    public ClientInputThread() {
        try {
            //Socket
            InetAddress multicastGroup = InetAddress.getByName(ServerHandler.groupIP);
            int multicastPort = ServerHandler.multicastPort;
            dgram = new DatagramPacket(buffer, buffer.length, multicastGroup, multicastPort);
            udpMulticastSocket = new MulticastSocket(multicastPort);
            udpMulticastSocket.joinGroup(multicastGroup);

            connectionSocket = new DatagramSocket();

        }catch(IOException e){
            System.out.println("Error while creating ClientInputThread Socket.");
        }

    }

    @Override
    public void run() {
        running = true;
        while(running) {
            try {

                if(ClientConnectionHandler.isConnected()) {
                    System.out.println("we in here");
                    udpMulticastSocket.receive(dgram);
                }
                else{
                    System.out.println("we out here");
                    connectionSocket.receive(dgram);
                }

                System.out.println("packet recieved");

                byte[] compoundData = dgram.getData();

                //split recieved data into identifier and packetdata
                byte[] identifier = Arrays.copyOfRange(compoundData, 0, NetworkingIdentifiers.IDENTIFIER_LENGTH);
                byte[] packetData = Arrays.copyOfRange(compoundData,
                        NetworkingIdentifiers.IDENTIFIER_LENGTH, compoundData.length);


                //test print identifier
                for(byte el : identifier) System.out.print(el);


                if(Arrays.equals(identifier, NetworkingIdentifiers.ACCEPT_IDENTIFIER)){
                    handleConnectionConfirm(packetData);
                }
                else if(Arrays.equals(identifier, NetworkingIdentifiers.REJECTION_IDENTIFIER)){
                    handleConnectionReject(packetData);
                }
                else if(Arrays.equals(identifier, NetworkingIdentifiers.SIMULATION_STATE_IDENTIFIER)){
                    handleStateInput(packetData);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleConnectionConfirm(byte[] packetData){
        String message = new String(packetData);
        System.out.println("Confirm Message: " + message);
    }

    private void handleConnectionReject(byte[] packetData){
        String message = new String(packetData);
        System.out.println("Reject Message: " + message);
    }

    private void handleStateInput(byte[] packetData) throws IOException{

        ByteArrayInputStream bais = new ByteArrayInputStream(packetData);
        ObjectInputStream ois = new ObjectInputStream(bais);
        try {
            ServerOutput clientInput = (ServerOutput) ois.readObject();
            //do stuff with recieved state
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("CLIENT Error reading object");
        }
    }

    public void close(){
        running = false;
    }
}
