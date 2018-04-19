package ServerNetworking.GameServer;

import Input.PlayerInput;

import java.net.InetAddress;

public class PlayerSocket {

    InetAddress playerIP;
    int playerID;
    PlayerInput inputSource;

    public PlayerSocket(InetAddress playerIP, int playerID){
        this.playerIP = playerIP;
    }

    public InetAddress getPlayerIP() {
        return playerIP;
    }

    public void setInputSource(PlayerInput pInput){
        inputSource = pInput;
    }

    public int getPlayerID(){
        return playerID;
    }
}
