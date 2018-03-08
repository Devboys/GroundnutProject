package Code;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class PublicServerThread extends Thread {

    private DatagramSocket socket;
    private byte[] buf = new byte[256];
    private ArrayList<InetAddress> IPaddressArray = new ArrayList<InetAddress>();
    private InetAddress group;
    private ArrayList<Integer> ports = new ArrayList<>();

    private int serverPort = 24000;
    private int clientPort = 24001;

    public PublicServerThread() throws IOException {
        socket = new DatagramSocket(serverPort);
        group = InetAddress.getByName("230.0.0.0");
    }

    public void run() {
        while (true) {
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                System.out.println("package received");

                byte[] buf = packet.getData();

                DatagramPacket newpacket = new DatagramPacket(buf,buf.length, group, clientPort);
                socket.send(newpacket);

                System.out.println("package sent");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
