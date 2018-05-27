package ClientNetworking.ConnectionClient;

import ClientNetworking.ClientNetworkingManager;
import ClientNetworking.NetworkingHandler;

/**Handles the connection-procedure between the game-server and this client. A hostIP must have been provided to the
 * ClientNetworkingManager before this can establish a proper connection.*/
public class ClientConnectionHandler implements NetworkingHandler {

    private ClientConnectionInput clientIn; //recieves connection-confirms and rejects from server.
    private ClientConnectionOutput clientOut; //prints connection-requests to server

    public ClientConnectionHandler(ClientNetworkingManager parent){

        clientIn = new ClientConnectionInput(parent);
        clientOut = new ClientConnectionOutput(parent);

        clientIn.start();
        clientOut.start();
    }

    @Override public void close() {
        clientIn.close();
        clientOut.close();
    }
}
