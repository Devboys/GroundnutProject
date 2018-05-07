package ClientNetworking.GameClient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;

import ClientNetworking.ClientNetworkingHandler;
import ClientNetworking.ConnectionState;
import Constants.NetworkingIdentifiers;
import ServerNetworking.GameServer.ServerHandler;
import ServerNetworking.GameServer.ServerOutput;

public class ClientServerInput extends Thread {

    //Data
    private final int BUFFER_LENGTH = 256;
    private byte[] buffer = new byte[BUFFER_LENGTH];

    //multicast Socket
    private MulticastSocket udpMulticastSocket;
    private DatagramPacket dgram;

    private boolean isRunning;

    public ClientServerInput() {
        try {
            //Socket
            InetAddress multicastGroup = InetAddress.getByName(ServerHandler.groupIP);
            int multicastPort = ServerHandler.multicastPort;
            dgram = new DatagramPacket(buffer, buffer.length, multicastGroup, multicastPort); //fix

            udpMulticastSocket = new MulticastSocket(multicastPort);
            udpMulticastSocket.joinGroup(multicastGroup);

        }catch(IOException e){
            System.out.println("Error while creating ClientServerInput Socket.");
        }

    }

    @Override
    public void run() {
        isRunning = true;
        while(isRunning) {
            try {
                if(ClientNetworkingHandler.getState() == ConnectionState.CONNECTED) {
                    udpMulticastSocket.receive(dgram);

                    byte[] compoundData = dgram.getData();

                    //split recieved data into identifier and packetdata
                    byte[] identifier = Arrays.copyOfRange(compoundData, 0, NetworkingIdentifiers.IDENTIFIER_LENGTH);
                    byte[] packetData = Arrays.copyOfRange(compoundData,
                            NetworkingIdentifiers.IDENTIFIER_LENGTH, compoundData.length);

                    //ignore all other packages than states.
                    if(Arrays.equals(identifier, NetworkingIdentifiers.SIMULATION_STATE_IDENTIFIER)){
                        handleStateInput(packetData);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleStateInput(byte[] packetData) throws IOException{
        ByteArrayInputStream bais = new ByteArrayInputStream(packetData);
        ObjectInputStream ois = new ObjectInputStream(bais);

        System.out.println("state recieved");

        try {
            ServerOutput clientInput = (ServerOutput) ois.readObject();

        } catch (Exception e){
            e.printStackTrace();
            System.out.println("CLIENT Error reading object");
        }
    }

    public void close(){
        isRunning = false;
    }
}
