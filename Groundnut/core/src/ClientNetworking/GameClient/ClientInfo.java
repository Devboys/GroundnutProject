package ClientNetworking.GameClient;

import Input.PlayerInputHandler;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientInfo {

    private static InetAddress playerIP;
    private int PlayerNumber;

    public ClientInfo(){
        try {
            playerIP = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println("THIS IS MY IP: " + playerIP);
    }

    public static InetAddress getPlayerIP(){
        return playerIP;
    }

    public int getPlayerNumber() {
        return PlayerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        PlayerNumber = playerNumber;
    }
}
