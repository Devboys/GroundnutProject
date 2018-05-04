package Core;

import ClientNetworking.GameClient.ClientConnectionHandler;
import ClientNetworking.GameClient.ClientServerInput;
import ClientNetworking.GameClient.ClientServerOutput;
import ClientNetworking.LobbyClient.LobbyClientHandler;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.net.InetAddress;

public class GameLauncher {

    public static void launch(InetAddress HostIP, int Player){
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(new GameThread(), config);

        ClientConnectionHandler clientConnectionHandler = new ClientConnectionHandler(true);
    }
}