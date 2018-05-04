package Desktop;

import Core.GameThread;
import Input.PlayerInputHandler;
import Scenes.GameStateManager;
import ServerNetworking.GameServer.ServerHandler;
import ServerNetworking.GameServer.ServerInputThread;
import ServerNetworking.GameServer.ServerOutputThread;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.io.IOException;

public class ServerLauncher {
    public static void main(String[] args){
        launch();
    }

    public static void launch(){
        //start serverside simulation
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(new GameThread(), config);

        //setup input/output threads
        ServerOutputThread serverOutput = new ServerOutputThread();
        ServerInputThread serverInput = new ServerInputThread();
        serverOutput.start();
        serverInput.start();
    }
}