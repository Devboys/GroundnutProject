package ClientNetworking.GameClient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import ServerNetworking.GameServer.ServerHandler;
import ServerNetworking.GameServer.ServerOutput;

public class ClientInputThread extends Thread {

    //Data
    private final int BUFFER_LENGTH = 256;
    private byte[] buffer = new byte[BUFFER_LENGTH];

    //Socket
    private MulticastSocket udpMulticastSocket;
    private DatagramPacket dgram;
    private InetAddress multicastGroup;
    private int multicastPort;

    public ClientInputThread() {
        try {
            //Socket
            multicastGroup = InetAddress.getByName(ServerHandler.getGroup());
            multicastPort = ServerHandler.getMulticastPort();
            dgram = new DatagramPacket(buffer, buffer.length, multicastGroup, multicastPort);
            udpMulticastSocket = new MulticastSocket(multicastPort);
            udpMulticastSocket.joinGroup(multicastGroup);
            System.out.println("CLIENT Created udpSocket on multicast address "
                                + multicastGroup + " : " + multicastPort);
        }catch(IOException e){
            System.out.println("Error while creating ClientInputThread Socket.");
        }

    }

    @Override
    public void run() {
        while(true) {
            //System.out.println("CLIENT Waiting for datagram to be received...");
            try {
                udpMulticastSocket.receive(dgram);
                //System.out.println("CLIENT Datagram received");
                ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
                ObjectInputStream ois = new ObjectInputStream(bais);
                try {
                    ServerOutput clientInput = (ServerOutput) ois.readObject();
//                    ClientGameState.updateClientState(clientInput);
                } catch (Exception e){
                    e.printStackTrace();
                    System.out.println("CLIENT Error reading object");
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error in ClientInputThread");
            }
            try {
                Thread.sleep(ServerHandler.getClientTickRate());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
