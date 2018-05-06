package ClientNetworking;

import ClientNetworking.ConnectionClient.ClientConnectionHandler;
import ClientNetworking.GameClient.ClientServerHandler;
import ClientNetworking.LobbyClient.ClientLobbyHandler;

import java.net.InetAddress;

//MAKE NOT STATIC
public class ClientNetworkingHandler {

    private static InetAddress gameHostIP;
    private static ConnectionState currState = ConnectionState.DISCONNECTED;
    private static NetworkingHandler currHandler;

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

    public static ConnectionState getState(){
        return currState;
    }

    public static void setState(ConnectionState state){
        if(currHandler != null) currHandler.close();

        switch (state){
            case CONNECTED:
                if(gameHostIP != null) {
                    currHandler = new ClientServerHandler();
                } else {
                    setState(ConnectionState.DISCONNECTED);
                    System.out.println("No host IP, switching to disconnected");
                }
                break;
            case INLOBBY:
                currHandler = new ClientLobbyHandler();
                break;
            case CONNECTING:
                if(gameHostIP != null) {
                    currHandler = new ClientConnectionHandler();
                } else {
                    setState(ConnectionState.DISCONNECTED);
                    System.out.println("No host IP, switching to disconnected");
                }
                break;
            case DISCONNECTED:
                currHandler = null;
                break;
        }

        System.out.println("CLIENT: switching to: "+ state.name());
        currState = state;
    }

    public static void setHostIP(InetAddress ip){ gameHostIP = ip; }
    public static InetAddress getHostIP(){ return gameHostIP; }
}
