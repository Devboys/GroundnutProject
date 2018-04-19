package Desktop;

import Core.RenderThread;
import Input.PlayerInputHandler;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ClientNetworking.GameClient.ClientInfo;
import ClientNetworking.GameClient.ClientInputThread;
import ClientNetworking.GameClient.ClientOutputThread;
import ClientNetworking.LobbyClient.LobbyClientMain;

import java.net.UnknownHostException;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new RenderThread(), config);

		ClientInfo clientInfo = new ClientInfo();

		ClientOutputThread clientOutput = new ClientOutputThread();
		clientOutput.start();
		ClientInputThread clientInput = new ClientInputThread();
		clientInput.start();
		//LobbyClientMain lobbyMain = new LobbyClientMain();
	}
}
