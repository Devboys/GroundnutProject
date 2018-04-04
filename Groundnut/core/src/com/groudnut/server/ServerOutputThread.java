package com.groudnut.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerOutputThread extends Thread{

    private DatagramSocket udpSocket;
    private InetAddress group;

    public ServerOutputThread() {
        udpSocket = NetworkHandler.getSocket();
        group = NetworkHandler.getGroup();
    }

    public void run(){
        boolean running = true;

        while(running) {
            byte[] buf = new byte[256];
            try {
                DatagramPacket sendPacket = new DatagramPacket(buf, buf.length, group, NetworkHandler.getClientPort());
                udpSocket.send(sendPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}