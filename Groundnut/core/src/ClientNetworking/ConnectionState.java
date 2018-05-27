package ClientNetworking;

/**List of all connection states that a client can be in.*/
public enum ConnectionState {
    DISCONNECTED,   //no connection
    INLOBBY,        //Communication with lobby
    CONNECTING,     //Attempt connetion with server
    CONNECTED;      //Communication with server.
}
