package com.mygdx.game;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ClientMain extends Thread {
    protected MulticastSocket MultiSocket = null;
    protected byte[] buffer = new byte[256];

    private DatagramSocket socket;
    private InetAddress address;

    public ClientMain() throws IOException{
        socket = new DatagramSocket();
        address = InetAddress.getByName("localhost");
    }

    public void run(){
        try {
            MultiSocket = new MulticastSocket(6112);
             InetAddress group = InetAddress.getByName("230.0.0.0");
             MultiSocket.joinGroup(group);

             while(true){
                 DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                 MultiSocket.receive(packet);
                 System.out.println("Jeppe was here");
                 String output = new String(packet.getData(),0,packet.getLength());
                 System.out.println(output);
             }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
