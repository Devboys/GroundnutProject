package ClientNetworking.GameClient;

import ClientNetworking.LobbyClient.LobbyClientHandler;

public class ClientConnectionHandler {

    private static ConnectionState currState = ConnectionState.DISCONNECTED;

    public ClientConnectionHandler(boolean connectThroughLobby){
        if(connectThroughLobby){
            startLobbyConnection();
            setState(ConnectionState.INLOBBY);
        }

        else{
            startServerConnection();
        }
    }

    public static ConnectionState getState(){
        return currState;
    }

    //MAYBE A WIERD IDEA = NOT VERY SCALABLE BUT LOOKS BETTER WHEN STATES ARE LIMITED.
    public static boolean isConnected(){
        return currState == ConnectionState.CONNECTED;
    }
    public static boolean isConnecting(){
        return currState == ConnectionState.CONNECTING;
    }
    public static boolean isDisconnected(){
        return currState == ConnectionState.DISCONNECTED;
    }
    public static boolean isInLobby() { return currState == ConnectionState.INLOBBY; }

    public static void setState(ConnectionState state){
        currState = state;
    }


    public void startServerConnection(){
        ClientServerInput clientInput = new ClientServerInput();
        clientInput.start();

        ClientServerOutput clientOutput = new ClientServerOutput();
        clientOutput.start();
    }

    public void startLobbyConnection(){
        LobbyClientHandler lobbyClient = new LobbyClientHandler();
    }
}
