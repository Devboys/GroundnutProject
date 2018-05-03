package Desktop;

import ClientNetworking.LobbyClient.LobbyClientMain;
import Core.GameThread;
import Input.PlayerInput;
import Input.PlayerInputHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ClientNetworking.GameClient.ClientInputThread;
import ClientNetworking.GameClient.ClientOutputThread;

public class DesktopLauncher {
    public static void main (String[] arg) {

        LobbyClientMain test = new LobbyClientMain();
    }
}
