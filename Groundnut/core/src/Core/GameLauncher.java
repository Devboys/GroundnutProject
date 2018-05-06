package Core;

import ClientNetworking.ClientNetworkingHandler;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.net.InetAddress;

public class GameLauncher {

    public static void launch(InetAddress HostIP, int Player){
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(new GameThread(), config);

        ClientNetworkingHandler clientNetworkingHandler = new ClientNetworkingHandler();
    }
}
