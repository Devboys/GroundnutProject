package com.groudnut.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import com.groudnut.server.NetworkHandler;

public class ClientInputThread extends Thread {

    private MulticastSocket multiSocket;
    private InetAddress groupAddress;

    String packetInputData;

    private static int[][] playerPositions = new int[4][2];

    static int coordX = 250;
    static int coordY = 250;

    public ClientInputThread() {
        try {
            multiSocket = NetworkHandler.getMultiSocket();
            groupAddress = NetworkHandler.getGroup();
            multiSocket.joinGroup(groupAddress);
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
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                multiSocket.receive(packet);
                System.out.println("Package received " + packet.getAddress() + " : " + packet.getPort());
                packetInputData = new String(packet.getData());
                System.out.println(packetInputData);
                String[] stringCoords = packetInputData.split("@");
                System.out.println(stringCoords.length);

                playerPositions[0][0] = Integer.parseInt(stringCoords[1]);
                playerPositions[0][1] = Integer.parseInt(stringCoords[2]);

                playerPositions[1][0] = Integer.parseInt(stringCoords[3]);
                playerPositions[1][1] = Integer.parseInt(stringCoords[4]);

                playerPositions[2][0] = Integer.parseInt(stringCoords[5]);
                playerPositions[2][1] = Integer.parseInt(stringCoords[6]);

                playerPositions[3][0] = Integer.parseInt(stringCoords[7]);
                playerPositions[3][1] = Integer.parseInt(stringCoords[8]);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static int[][] getPositions() {
        return playerPositions;
    }

    public static int getX(){
        return coordX;
    }

    public static int getY(){
        return coordY;
    }
}
