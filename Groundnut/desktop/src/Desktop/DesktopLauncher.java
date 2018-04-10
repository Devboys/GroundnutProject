package Desktop;

import Core.RenderThread;
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

		try {
			ClientInfo clientInfo = new ClientInfo();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		ClientInputThread clientInput = new ClientInputThread();
		ClientOutputThread clientOutput = new ClientOutputThread();
		clientInput.start();
		clientOutput.start();

		LobbyClientMain lobbyMain = new LobbyClientMain();
	}
}
