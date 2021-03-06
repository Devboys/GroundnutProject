package ClientNetworking;

import com.badlogic.gdx.Gdx;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import static com.badlogic.gdx.Input.Keys.*;

public class ClientWriterThread extends Thread {

    protected MulticastSocket multiSocket = null;
    protected byte[] buffer = new byte[256];

    private DatagramSocket socket;
    private InetAddress address;

    int locY = 100;
    int locX = 50;


    private int serverPort = 24000;

    public ClientWriterThread(){
        try {
            socket = new DatagramSocket();
            address = InetAddress.getByName("127.0.0.1");

        }catch(IOException e){}
    }

    public void run() {

        while(true) {
            if (Gdx.input.isKeyPressed(UP)) {
                locY++;
                translate();
            }
            else if (Gdx.input.isKeyPressed(DOWN)){
                locY--;
                translate();
            }
            else if (Gdx.input.isKeyPressed(RIGHT)){
                locX++;
                translate();
            }
            else if (Gdx.input.isKeyPressed(LEFT)){
                locX--;
                translate();
            }
        }
    }

    public void translate() {
        try {
            String message = "@" +locX + "@" + locY ;
            buffer = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, serverPort);
            socket.send(packet);

            System.out.println("Message sent to server: " + packet.getData());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
