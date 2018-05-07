package ClientNetworking.GameClient;

import ClientNetworking.ClientNetworkingHandler;
import ClientNetworking.NetworkingHandler;

/**Handles input-sending and state-receiving between the client and the server. A hostIP must have been provided to the
 * ClientNetworkingHandler before this can establish a proper connection.*/
public class ClientServerHandler implements NetworkingHandler {

    private ClientServerInput clientIn;   //recieves simulation-states from server.
    private ClientServerOutput clientOut; //sends player-input objects to server.

    public ClientServerHandler(){
        clientIn = new ClientServerInput();
        clientOut = new ClientServerOutput(ClientNetworkingHandler.getHostIP());

        clientIn.start();
        clientOut.start();
    }

    @Override public void close() {
        clientIn.close();
        clientOut.close();
    }
}
