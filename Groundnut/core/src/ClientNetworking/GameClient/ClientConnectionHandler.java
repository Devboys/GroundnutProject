package ClientNetworking.GameClient;

public class ClientConnectionHandler {

    //connection state
    public enum ConnectionState{
        DISCONNECTED,
        CONNECTING,
        CONNECTED
    }

    private static ConnectionState currState = ConnectionState.DISCONNECTED;

    public static ConnectionState getState(){
        return currState;
    }

    public static boolean isConnected(){
        return currState == ConnectionState.CONNECTED;
    }
    public static boolean isConnecting(){
        return currState == ConnectionState.CONNECTING;
    }
    public static boolean isDisconnected(){
        return currState == ConnectionState.DISCONNECTED;
    }

    public static void switchState(ConnectionState state){
        currState = state;
    }

}
