package ClientNetworking.GameClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import ServerNetworking.GameServer.ServerHandler;

public class ClientOutputThread extends Thread {

    //Data
    private byte[] buffer;

    //Socket
    private DatagramSocket udpSocket;
    private DatagramPacket dgram;
    private InetAddress serverIP;
    private int serverPort;

    public ClientOutputThread(){
        try {
            //Socket
            serverIP = InetAddress.getByName(ServerHandler.getServerIp());
            serverPort = ServerHandler.getGamePort();
            udpSocket = new DatagramSocket();
            udpSocket.connect(serverIP,serverPort);
            System.out.println("CLIENT Created udpSocket on address " + serverIP + ":" + serverPort);
        }catch(IOException e){
            System.out.println("SERVER Error creating Output datagram udpSocket or data stream.");
        }
    }

    @Override
    public void run() {
        while(true){
            try {
                //Data
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);

                oos.writeObject(new ClientOutput());
                oos.flush();
                buffer = baos.toByteArray();
                dgram = new DatagramPacket(buffer, buffer.length, serverIP, serverPort);
                udpSocket.send(dgram);
                System.out.println("CLIENT Sent Data length: " + buffer.length);
                baos.reset();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(ServerHandler.getClientTickRate());
            } catch (InterruptedException e){
                e.printStackTrace();
                System.out.println("Error in ClientOutputThread");
            }
        }
    }
}
