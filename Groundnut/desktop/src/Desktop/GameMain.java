package Desktop;

import ClientNetworking.ClientNetworkingManager;
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

        ClientNetworkingManager clientNetworkingManager = new ClientNetworkingManager();
        SimulationHandler.getInstance().startSimulation(false);
    }

}
