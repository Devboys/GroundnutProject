package Core;

import Entity.PlayerGroup;
import Input.PlayerInput;
import Input.PlayerInputHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class SimulationHandler {

    private static SimulationHandler instance;
    private SimulationHandler(){}

    private static boolean serverSide;
    private PlayerInput clientInput;
    private PlayerGroup playerGroup;

    public static SimulationHandler getInstance(){
        if(instance == null){
            instance = new SimulationHandler();
        }
        return instance;
    }

    //public init call, simulationhandler should be the first thing initialized
    public void startSimulation(Boolean isServerSide){
        serverSide = isServerSide;

        if(serverSide){
            startServerSim();
        }
        else{
            startClientSim();
        }
    }

    private void startServerSim(){

        playerGroup = new PlayerGroup();

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "SERVER SIMULATION";
        new LwjglApplication(new GameThread(), config);

    }

    private void startClientSim(){
        playerGroup = new PlayerGroup();

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "CLIENT SIMULATION";
        new LwjglApplication(new GameThread(), config);

        clientInput = new PlayerInput();
        Gdx.input.setInputProcessor(new PlayerInputHandler(clientInput));

        playerGroup.insertPlayer(0);
        playerGroup.getPlayer(0).setInputSource(clientInput);
    }

    public static boolean isServerSide() { return serverSide; }

    public PlayerGroup getPlayers(){
        return playerGroup;
    }

    public PlayerInput getPlayerInput(){
        return clientInput;
    }

}