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

    private int serverPort = 24000;
    private int clientPort = 24001;

    public ClientMain(){
        try {
            socket = new DatagramSocket();
            address = InetAddress.getByName("10.60.30.188");
        }catch(IOException e){}
    }

    public void run(){
        try {
            //setup listener sockets
             MultiSocket = new MulticastSocket(clientPort);
             InetAddress group = InetAddress.getByName("230.0.0.0");
             MultiSocket.joinGroup(group);

             String message = "Hello";
             buffer = message.getBytes();
             DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, serverPort);
             socket.send(packet);

            System.out.println("Message sent to server: " + packet.getData());

             while(true){
                 buffer = new byte[256];
                 packet = new DatagramPacket(buffer, buffer.length);
                 MultiSocket.receive(packet);
                 String output = new String(packet.getData(),0,packet.getLength());
                 System.out.println("Recieved from server: " + output);
             }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
