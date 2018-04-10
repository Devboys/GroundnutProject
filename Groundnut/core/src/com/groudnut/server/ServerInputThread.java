package com.groudnut.server;

import com.groudnut.client.ClientOutput;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;


public class ServerInputThread extends Thread {

    //Server Info
    private final int bufferSize = 1024;

    //Create Socket
    private DatagramSocket udpSocket;
    private int serverPort;
    private ArrayList<InetAddress> playerIPs = ServerHandler.getClientIPs();

    public ServerInputThread(){
        try {
            serverPort = ServerHandler.getGamePort();
            udpSocket = new DatagramSocket(serverPort);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            System.out.println("SERVER Waiting for datagram to be received...");
            try {
                byte[] buffer = new byte[bufferSize];
                udpSocket.receive(new DatagramPacket(buffer, buffer.length));
                System.out.println("SERVER Datagram received");

                //Deserialize
                ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
                ObjectInputStream ois = new ObjectInputStream(bais);
                try{
                    Object objectFromClient = ois.readObject();
                    ClientOutput serverInput = (ClientOutput) objectFromClient;
                    if (playerIPs.isEmpty()){
                        ServerHandler.addIP(serverInput.getClientIP());
                        System.out.println("SERVER IP: " + serverInput.getClientIP() + " added to " + playerIPs);
                    } else if(!playerIPs.isEmpty() && ServerHandler.getConnectedPlayers() < 4){
                        for(int i = 0; i < ServerHandler.getConnectedPlayers(); i++) {
                            if(!serverInput.getClientIP().equals(playerIPs.get(i))){
                                ServerHandler.addIP(serverInput.getClientIP());
                                System.out.println("SERVER IP: " + serverInput.getClientIP() + " added to " + playerIPs);
                            }
                        }
                    } else {
                        System.out.println(playerIPs);
                    }
                    GameState.updateGameState(serverInput); //Update State
                } catch (Exception e){
                    System.out.println("SERVER Player IPs:" + playerIPs);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}