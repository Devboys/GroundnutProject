package com.mygdx.game;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

public class ClientListenerThread extends Thread {

    private MulticastSocket multiSocket;
    private InetAddress serverAddress;
    private InetAddress groupAdress;

    private String packetInputData;

    public static final int numDimensions = 2;

    public ClientListenerThread() {
        try {
            multiSocket = new MulticastSocket();
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
                packetInputData = new String(readPacket.getData());


                System.out.println(getPositions());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int[][] getPositions(){
        String[] tempPositions = packetInputData.split("|");

        int[][] playerPositions = new int[tempPositions.length][numDimensions];
        String[] currentPosition;
        for(int i = 0; i < tempPositions.length; i++) {
            currentPosition = tempPositions[i].split(",");

            for(int j = 0; j < 2; j++) {
                playerPositions[i][j] = Integer.parseInt(currentPosition[j]);
            }
        }

        return playerPositions;
    }
}
