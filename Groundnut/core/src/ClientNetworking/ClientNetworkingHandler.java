package ClientNetworking;

import ClientNetworking.ConnectionClient.ClientConnectionHandler;
import ClientNetworking.GameClient.ClientServerHandler;
import ClientNetworking.LobbyClient.ClientLobbyHandler;
import Entity.Player;
import Input.PlayerInput;

import java.net.InetAddress;

//TODO: REDO CONNECTION-PORTS ON SERVER AS WELL AS CLIENT.

/**Super-handler for client-server networking. Handles the client's connection-state and opens handlers for
 * different networking tasks, like connecting to a lobby.
 * Also holds the game-server's host-IP when one is provided. */
public class ClientNetworkingHandler {

    private InetAddress gameHostIP;
    private ConnectionState currState;
    private NetworkingHandler currHandler;

    /**No argument constructor will attempt to connect through lobby.*/
    public ClientNetworkingHandler(){
        setState(ConnectionState.INLOBBY);
    }

    /**Starts the connection protocol between the client and the server at the given IP.
     * @param hostIP the IP of the server-host*/
    public ClientNetworkingHandler(InetAddress hostIP){
        gameHostIP = hostIP;
        setState(ConnectionState.CONNECTING);
    }

    /** @return This object's current ConnectionState */
    public ConnectionState getState(){
        return currState;
    }

    /**Switches the current state to the given state and initiates whatever NetworkingHandler is tied to the given
     * state. The previous handler is closed upon the switch. CONNECTED and CONNECTING requires that a hostIP has been
     * provided previously. Otherwise, they will switch to DISCONNECTED instead.
     * @param state the state to switch to.
     */
    public void setState(ConnectionState state){
        if(currHandler != null) currHandler.close();

        System.out.println("CLIENT: switching to: "+ state.name());
        currState = state;

        switch (state){
            case CONNECTED:
                if(gameHostIP != null) {  //server-connection requires a hostIP to send packages to.
                    currHandler = new ClientServerHandler(this);
                } else {
                    setState(ConnectionState.DISCONNECTED);
                    System.out.println("No host IP, switching to disconnected");
                }
                break;
            case INLOBBY:
                currHandler = new ClientLobbyHandler(this);
                break;
            case CONNECTING:
                if(gameHostIP != null) { //server-connection requires a hostIP to send packages to.
                    currHandler = new ClientConnectionHandler(this);
                } else {
                    setState(ConnectionState.DISCONNECTED);
                    System.out.println("No host IP, switching to disconnected");
                }
                break;
            case DISCONNECTED:
                currHandler = null;
                break;
        }
    }

    public void setHostIP(InetAddress ip){ gameHostIP = ip; }
    public InetAddress getHostIP(){ return gameHostIP; }
}
