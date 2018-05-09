package Desktop;

import ClientNetworking.ClientNetworkingHandler;
import Core.SimulationHandler;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class GameMainNoLobby {
    public static void main(String[] args) throws UnknownHostException {
        ClientNetworkingHandler networkHandler = new ClientNetworkingHandler(InetAddress.getByName("localhost"));
        SimulationHandler.getInstance().startSimulation(false);
    }
}
