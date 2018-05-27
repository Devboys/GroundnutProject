package ServerNetworking.GameServer;

import Input.InputSource;
import Input.PlayerInput;

import java.net.InetAddress;

public class PlayerSocket implements InputSource{

    private boolean isConnected;

    private InetAddress playerIP;
    private PlayerInput playerInput;

    /**Simple data-collection of client-related data. Implements InputSource for use in player-movement in game.*/
    public PlayerSocket(){
        playerInput = new PlayerInput();
        isConnected = false;
    }

    /**@return whether the client-socket has been given an IP-address.*/
    public boolean isConnected(){ return isConnected; }
    public void setConnected(boolean t){isConnected = t;}

    /**@return the client's registered IP-address*/
    public InetAddress getPlayerIP() { return playerIP; }
    public void setPlayerIP(InetAddress IP){ playerIP = IP; }

    /**Sets the input-source of the client to the given*/
    public void setInput(PlayerInput pInput){
        playerInput = pInput;
    }
    @Override public PlayerInput getInput(){ return playerInput; }
}
