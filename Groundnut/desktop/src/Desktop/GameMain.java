package Desktop;

import ClientNetworking.ClientNetworkingHandler;
import Core.SimulationHandler;


public class GameMain {

    public static void main(String[] args){
        ClientNetworkingHandler clientNetworkingHandler = new ClientNetworkingHandler();
        SimulationHandler.getInstance().startSimulation(false);
    }

}
