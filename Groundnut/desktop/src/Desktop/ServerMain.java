package Desktop;

import Core.SimulationHandler;

public class ServerMain {
    public static void main(String[] args){
        launch();
    }

    public static void launch(){
        SimulationHandler.getInstance().startSimulation(true);

    }
}