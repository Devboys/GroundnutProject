package ServerNetworking.GameServer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;


public class ServerOutputThread extends Thread {

    //Prepare Multicast Socket
    private MulticastSocket udpMulticastSocket;
    private InetAddress multicastGroupIP;
    private int multicastPort;

    //Prepare Data Stream
    private ByteArrayOutputStream baos;
    private ObjectOutputStream oos;

    public ServerOutputThread() throws IOException {
        try {
            //Socket
            multicastGroupIP = InetAddress.getByName(ServerHandler.getGroup());
            multicastPort = ServerHandler.getMulticastPort();
            udpMulticastSocket = new MulticastSocket();
            udpMulticastSocket.joinGroup(multicastGroupIP);
            System.out.println("SERVER Created socket on address " + multicastGroupIP + ":" + multicastPort);

            //Data Stream
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
        } catch (IOException e) {
            System.out.println("SERVER Error creating Output multicast socket or data stream.");
        }
    }

    @Override
    public void run(){
        while(true) {
            try {
            ServerOutput serverOutput = new ServerOutput();
            oos.writeObject(serverOutput); //Server Output
            byte[] data = baos.toByteArray();
                udpMulticastSocket.send(new DatagramPacket(data, data.length, multicastGroupIP, multicastPort));
                System.out.println("SERVER Sent Data: " + data + ". Data Length: " + data.length + ". Multicast Group: "
                        + multicastGroupIP + ":" + multicastPort);
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