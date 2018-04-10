package com.groudnut.server;

import java.net.InetAddress;
import java.util.ArrayList;

public class ServerHandler {

    private static final int gamePort = 24000;
    private static final int multicastPort = 24002;
    private static final int maxPlayerCount = 4;
    private static int connectedPlayers = 0;
    private static final int tickRate = 5000; // 1 = 1ms
    private static final String serverIp = "127.0.0.1";
    private static final String group = "230.0.0.0";
    private static ArrayList<InetAddress> clientIPs = new ArrayList<InetAddress>(maxPlayerCount);

    public static void addIP(InetAddress IP) {
        if(connectedPlayers < maxPlayerCount) {
            clientIPs.add(IP);
            connectedPlayers++;
        } else{
            System.out.println("Too many players.");
        }
    }

    public static int getMaxPlayerCount() { return maxPlayerCount; }
    public static int getConnectedPlayers() { return connectedPlayers; }
    public static ArrayList<InetAddress> getClientIPs() {
        return clientIPs;
    }
    public static String getGroup() {
        return group;
    }
    //public static int getPlayerCount() { return playerCount; }
    public static int getGamePort() { return gamePort; }
    public static int getMulticastPort() { return multicastPort; }
    public static int getTickRate(){return tickRate; }
    public static String getServerIp() { return serverIp; }
}