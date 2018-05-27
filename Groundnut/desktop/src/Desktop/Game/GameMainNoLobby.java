package Desktop.Game;

import ClientNetworking.ClientNetworkingManager;
import Core.SimulationHandler;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class GameMainNoLobby {
    public static void main(String[] args) throws UnknownHostException {
        ClientNetworkingManager networkHandler = new ClientNetworkingManager(InetAddress.getByName("localhost"));
        SimulationHandler.getInstance().startClientSim();
    }
}
