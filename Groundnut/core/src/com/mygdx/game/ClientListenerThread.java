package com.mygdx.game;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

public class ClientListenerThread extends Thread {

    private MulticastSocket multiSocket;
    private InetAddress groupAdress;

    String packetInputData;

    static int coordX;
    static int coordY;

    int clientPort = 24001;

    public ClientListenerThread() {
        try {
            multiSocket = new MulticastSocket(clientPort);
            groupAdress = InetAddress.getByName("230.0.0.0");
            multiSocket.joinGroup(groupAdress);
        }catch(IOException e){
            //handle this exception
        }
    }

    @Override
    public void run() {
        boolean running = true;
        while(running) {
            try {
                byte[] buffer = new byte[256];
                DatagramPacket readPacket = new DatagramPacket(buffer, buffer.length);
                multiSocket.receive(readPacket);

                System.out.println("package received");

                packetInputData = new String(readPacket.getData());

                String[] stringCoords = packetInputData.split("@");

                coordX = Integer.parseInt(stringCoords[0]);
                coordY = Integer.parseInt(stringCoords[1]);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getX(){
        return coordX;
    }

    public static int getY(){
        return coordY;
    }
}
