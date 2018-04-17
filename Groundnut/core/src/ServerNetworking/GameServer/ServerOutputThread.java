package ServerNetworking.GameServer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;


public class ServerOutputThread extends Thread {

    //Data
    private byte[] buffer;

    //Multicast Socket
    private MulticastSocket udpMulticastSocket;
    private DatagramPacket dgram;
    private InetAddress multicastGroupIP;
    private int multicastPort;

    public ServerOutputThread() throws IOException {
        try {
            //Socket
            multicastGroupIP = InetAddress.getByName(ServerHandler.getGroup());
            multicastPort = ServerHandler.getMulticastPort();
            udpMulticastSocket = new MulticastSocket();
            udpMulticastSocket.joinGroup(multicastGroupIP);
            System.out.println("SERVER Created socket on address " + multicastGroupIP + ":" + multicastPort);
        } catch (IOException e) {
            System.out.println("SERVER Error creating Output multicast socket or data stream.");
        }
    }

    @Override
    public void run(){
        while(true) {
            try {
                //Data
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);


                oos.writeObject(new ServerOutput());
                oos.flush();
                buffer = baos.toByteArray();
                dgram = new DatagramPacket(buffer, buffer.length, multicastGroupIP, multicastPort);
                udpMulticastSocket.send(dgram);
                System.out.println("SERVER Sent Data length: " + buffer.length);
                baos.reset();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(ServerHandler.getServerTickRate());
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Error in ServerOutputThread");
            }
        }
    }
}