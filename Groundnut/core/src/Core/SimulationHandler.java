package Core;

import Entity.PlayerGroup;
import Input.PlayerInput;
import Input.PlayerInputHandler;
import ServerNetworking.GameServer.GameStateSample;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

//TODO: MAKE ALL CLOSE() METHODS RELY ON A SIMULATION-HANDLER BOOLEAN (except lobby)

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

    //public initialize call, simulationhandler should be the first thing initialized
    public void startSimulation(Boolean isServerSide){
        serverSide = isServerSide;

        if(serverSide){
            startServerSim();
        }
        else{
            startClientSim();
        }
    }

    public void synchronizeSimulation(GameStateSample state){
        System.out.println("beep boop synchronizing");
        //TODO: SYNCHRONIZE METHOD.
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
