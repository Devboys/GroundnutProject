package Code;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class ServerListener extends Thread {

    DatagramSocket readSocket;
    byte[] buf = new byte[256];
    private InetAddress group;

    public ServerListener() {
        try {
            readSocket = new DatagramSocket(PublicMain.serverPort);
            group = InetAddress.getByName("230.0.0.0");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                readSocket.receive(packet);
                System.out.println("package recieved from " + packet.getAddress());


                packet = new DatagramPacket(packet.getData(), packet.getData().length, group, PublicMain.clientPort);
                readSocket.send(packet);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
