package ClientNetworking.GameClient;

import java.io.Serializable;
import java.net.InetAddress;

public class ClientOutput implements Serializable {

    private InetAddress clientIP = ClientInfo.getPlayerIP();
    private int playerNumber;

    public InetAddress getClientIP(){
        return clientIP;
    }

    public void setPlayer(int n){
        playerNumber = n;
    }

    public int getPlayerNumber(){
        return playerNumber;
    }
}
