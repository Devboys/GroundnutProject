package com.groudnut.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.groudnut.server.ServerHandler;

public class ClientOutputThread extends Thread {

    //Prepare Datagram Socket
    private DatagramSocket udpSocket;
    private InetAddress serverIP;
    private int serverPort;
   // private byte[] buffer = new byte[256];

    //Prepare Data Stream
    private ByteArrayOutputStream baos;
    private ObjectOutputStream oos;

    public ClientOutputThread(){
        try {
            //Socket
            serverIP = InetAddress.getByName(ServerHandler.getServerIp());
            serverPort = ServerHandler.getGamePort();
            udpSocket = new DatagramSocket();
            udpSocket.connect(serverIP,serverPort);
            System.out.println("CLIENT Created udpSocket on address " + serverIP + ":" + serverPort);

            //Data Stream
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
        }catch(IOException e){
            System.out.println("SERVER Error creating Output datagram udpSocket or data stream.");
        }
    }

    public void run() {
        while(true){
            try {
                ClientOutput clientOutput = new ClientOutput();
                oos.writeObject(clientOutput);
                byte[] data = baos.toByteArray();
                udpSocket.send(new DatagramPacket(data, data.length));
                System.out.println("CLIENT Sent Data: "+ data + ". Data Length: " + data.length + ". Multicast Group: "
                        + serverIP + ":" + serverPort);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(ServerHandler.getTickRate());
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
