package Desktop;

import Core.GameThread;
import Core.SimulationHandler;
import Input.PlayerInputHandler;
import Scenes.GameStateManager;
import ServerNetworking.GameServer.ServerHandler;
import ServerNetworking.GameServer.ServerInputThread;
import ServerNetworking.GameServer.ServerOutputThread;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.io.IOException;

public class ServerMain {
    public static void main(String[] args){
        launch();
    }

    public static void launch(){

//        //start serverside simulation - wrong
//        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//        config.title = "SERVER";
//        new LwjglApplication(new GameThread(), config);
//
        //setup input/output threads - MOVE TO SEPARATE HANDLER
        ServerOutputThread serverOutput = new ServerOutputThread();
        ServerInputThread serverInput = new ServerInputThread();
        serverOutput.start();
        serverInput.start();

        SimulationHandler.getInstance().startSimulation(true);
    }
}