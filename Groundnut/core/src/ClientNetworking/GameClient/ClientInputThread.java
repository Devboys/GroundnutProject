package ClientNetworking.GameClient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import ServerNetworking.GameServer.ServerHandler;

public class ClientInputThread extends Thread {

    //String packetInputData;
    //private static int[][] playerPositions = new int[4][2];
    //static int coordX = 250;
    //static int coordY = 250;
    final int bufferSize = 1024;

    //Create Socket
    private MulticastSocket udpMulticastSocket;
    private int multicastPort;
    private InetAddress multicastGroup;
    public ClientInputThread() {
        try {
            multicastPort = ServerHandler.getMulticastPort();
            multicastGroup = InetAddress.getByName(ServerHandler.getGroup());
            udpMulticastSocket = new MulticastSocket(multicastPort);
            udpMulticastSocket.joinGroup(multicastGroup);
        }catch(IOException e){
            //Handle
        }
    }

    //Receive data
    @Override
    public void run() {
        while(true) {
            System.out.println("CLIENT Waiting for datagram to be received...");
            try {
                byte[] buffer = new byte[bufferSize];
                udpMulticastSocket.receive(new DatagramPacket(buffer, bufferSize, multicastGroup, multicastPort));
                System.out.println("CLIENT Datagram received");

                //Deserialize object
                ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
                ObjectInputStream ois = new ObjectInputStream(bais);

                try {
                    Object readObjectFromServer = ois.readObject();
                    System.out.println("CLIENT Message is: " + readObjectFromServer);

                } catch (Exception e){
                    System.out.println("CLIENT No object read from UDP Datagram");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(ServerHandler.getTickRate());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
