package com.groudnut.server;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

public class NetworkHandler {

    private static final int serverPort = 24000;
    private static final int clientPort = 24001;
    private static final int playerCount = 4;

    private static MulticastSocket multiSocket;
    private static DatagramSocket socket;
    private static ArrayList<InetAddress> IPArray = new ArrayList<InetAddress>(4);
    private static InetAddress group;
    //private static ArrayList<Integer> port = new ArrayList<>();

    public NetworkHandler(){
        try {
            socket = new DatagramSocket(serverPort);
            multiSocket = new MulticastSocket(6789);
            group = InetAddress.getByName("230.0.0.0");
        } catch (IOException e){e.printStackTrace();}
    }

    public static MulticastSocket getMultiSocket() { return multiSocket; }
    public static int getClientPort() { return clientPort; }
    public static DatagramSocket getSocket() {
        return socket;
    }
    public static ArrayList<InetAddress> getIPArray() {
        return IPArray;
    }
    public static void setIPArray(ArrayList<InetAddress> IPArray) {
        NetworkHandler.IPArray = IPArray;
    }
    public static InetAddress getGroup() {
        return group;
    }
    public static int getPlayerCount() { return playerCount; }
   // public static ArrayList<Integer> getPort() { return port; }
    public static int getServerPort() { return serverPort; }
}