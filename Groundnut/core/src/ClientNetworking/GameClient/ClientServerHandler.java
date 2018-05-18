package ClientNetworking.GameClient;

import ClientNetworking.ClientNetworkingManager;
import ClientNetworking.NetworkingHandler;

/**Handles input-sending and state-receiving between the client and the server. A hostIP must have been provided to the
 * ClientNetworkingManager before this can establish a proper connection.*/
public class ClientServerHandler implements NetworkingHandler {

    private ClientServerInput clientIn;   //recieves simulation-states from server.
    private ClientServerOutput clientOut; //sends player-input objects to server.

    public ClientServerHandler(ClientNetworkingManager parent){

        clientIn = new ClientServerInput(parent);
        clientOut = new ClientServerOutput(parent);

        clientIn.start();
        clientOut.start();
    }

    @Override public void close() {
        clientIn.close();
        clientOut.close();
    }
}
