package ServerNetworking.GameServer;

import Input.InputSource;
import Input.PlayerInput;

import java.net.InetAddress;

public class PlayerSocket implements InputSource{

    private boolean isConnected;

    private InetAddress playerIP;
    private int playerIndex;
    private PlayerInput playerInput;

    public PlayerSocket(int playerIndex){
        playerInput = new PlayerInput();
        this.playerIndex = playerIndex;
        isConnected = false;
    }

    public boolean isConnected(){ return isConnected; }
    public void setConnected(boolean t){isConnected = t; }

    public int getPlayerIndex(){ return playerIndex; }

    public InetAddress getPlayerIP() { return playerIP; }
    public void setPlayerIP(InetAddress IP){ playerIP = IP; }

    public void setInput(PlayerInput pInput){
        playerInput = pInput;
    }
    @Override public PlayerInput getInput(){ return playerInput; }
}
