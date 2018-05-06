package ClientNetworking.ConnectionClient;

import ClientNetworking.ClientNetworkingHandler;
import ClientNetworking.NetworkingHandler;

public class ClientConnectionHandler implements NetworkingHandler {

    private ClientConnectionInput clientIn;
    private ClientConnectionOutput clientOut;

    public ClientConnectionHandler(){
        clientIn = new ClientConnectionInput();
        clientOut = new ClientConnectionOutput(ClientNetworkingHandler.getHostIP());

        clientIn.start();
        clientOut.start();
    }

    @Override public void close() {
        clientIn.close();
        clientOut.close();
    }
}
