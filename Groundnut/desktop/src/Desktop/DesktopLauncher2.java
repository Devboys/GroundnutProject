package Desktop;

import ClientNetworking.GameClient.ClientInputThread;
import ClientNetworking.GameClient.ClientOutputThread;
import Core.GameThread;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.net.InetAddress;

public class DesktopLauncher2 {

    public DesktopLauncher2(InetAddress HostIP, int Player){
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(new GameThread(), config);


        ClientInputThread clientInput = new ClientInputThread();
        clientInput.start();

        ClientOutputThread clientOutput = new ClientOutputThread();
        clientOutput.start();
    }

}
