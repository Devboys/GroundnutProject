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

public class ServerMain {

    public static void main(String[] args){
        //start serverside simulation
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(new GameThread(), config);

        //setup input/output threads
        ServerOutputThread serverOutput = new ServerOutputThread();
        serverOutput.start();

        ServerInputThread serverInput = new ServerInputThread();
        serverInput.start();
    }
}