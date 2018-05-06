package ClientNetworking.GameClient;

import ClientNetworking.ClientNetworkingHandler;
import ClientNetworking.NetworkingHandler;

public class ClientServerHandler implements NetworkingHandler {

    private ClientServerInput clientIn;
    private ClientServerOutput clientOut;

    public ClientServerHandler(){
        clientIn = new ClientServerInput();
        clientIn.start();

        clientOut = new ClientServerOutput(ClientNetworkingHandler.getHostIP());
        clientOut.start();
    }

    @Override public void close() {
        clientIn.close();
        clientOut.close();
    }
}
