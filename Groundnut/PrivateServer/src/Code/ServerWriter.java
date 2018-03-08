package Code;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class ServerWriter extends Thread{

    public static final int NUMDIMENSIONS = 2;
    public static final int MAXPLAYERS = 4;

    public static int[][] coords = new int[MAXPLAYERS][NUMDIMENSIONS];

    private InetAddress group;
    private DatagramSocket writeSocket;

    public ServerWriter() {
        try {
            writeSocket = new DatagramSocket(PublicMain.serverPort);
            group = InetAddress.getByName("230.0.0.0");
        }catch(IOException e) {e.printStackTrace();}
    }

    public void run(){
        boolean running = true;

        while(running) {
            byte[] buf = new byte[256];

            try {
                DatagramPacket sendPacket = new DatagramPacket(buf, buf.length, group, PublicMain.clientPort);
                writeSocket.send(sendPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
