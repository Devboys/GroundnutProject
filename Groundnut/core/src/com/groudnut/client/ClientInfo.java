package com.groudnut.client;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientInfo {

    private static InetAddress playerIP;
    private int PlayerNumber;

    public ClientInfo() throws UnknownHostException {
        playerIP = InetAddress.getLocalHost();
        System.out.println("THIS IS MY IP: " + playerIP);
    }

    public static InetAddress getPlayerIP() {
        return playerIP;
    }

    public int getPlayerNumber() {
        return PlayerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        PlayerNumber = playerNumber;
    }
}
