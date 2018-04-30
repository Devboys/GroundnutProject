package ServerNetworking.GameServer;

import Input.PlayerInput;

import java.net.InetAddress;

public class PlayerSocket {

    private boolean isConnected;

    private InetAddress playerIP;
    private int playerIndex;
    private PlayerInput inputSource;

    public PlayerSocket(int playerIndex){
        this.playerIndex = playerIndex;
        isConnected = false;
    }

    public boolean isConnected(){ return isConnected; }
    public void setConnected(boolean t){isConnected = t; }

    public int getPlayerIndex(){ return playerIndex; }

    public InetAddress getPlayerIP() { return playerIP; }
    public void setPlayerIP(InetAddress IP){ playerIP = IP; }

    public void setInputSource(PlayerInput pInput){
        inputSource = pInput;
    }
}
