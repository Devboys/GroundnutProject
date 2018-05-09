package Desktop;

import ClientNetworking.ClientNetworkingHandler;
import Core.SimulationHandler;
import Settings.SettingsReader;

import java.io.FileNotFoundException;


public class GameMain {

    public static void main(String[] args){
        try {
            SettingsReader.initValues();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ClientNetworkingHandler clientNetworkingHandler = new ClientNetworkingHandler();
        SimulationHandler.getInstance().startSimulation(false);
    }

}
