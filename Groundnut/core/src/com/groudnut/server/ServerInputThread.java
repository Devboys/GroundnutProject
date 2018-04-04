package com.groudnut.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class ServerInputThread extends Thread {

    DatagramSocket udpSocket = NetworkHandler.getSocket();
    byte[] buffer = new byte[256];
    private String[] userCommands = new String[4];

    public void run() {
        while (true) {
            try {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                udpSocket.receive(packet);
                System.out.println("Message received from client: " + packet.getSocketAddress());
                boolean found = false;

                if(!NetworkHandler.getIPArray().isEmpty()) {
                    for (int i = 0; i < NetworkHandler.getIPArray().size(); i++) {
                        if (packet.getAddress().equals(NetworkHandler.getIPArray().get(i))) {
                            found = true;
                            userCommands[i] = new String(packet.getData(), 0, packet.getLength());
                        }
                    }
                }
                if (!found) {
                    NetworkHandler.getIPArray().add(packet.getAddress());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}