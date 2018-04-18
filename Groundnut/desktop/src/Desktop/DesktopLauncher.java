package Desktop;

import Core.GameThread;
import Scenes.GameStateManager;
import Scenes.Scene;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ClientNetworking.GameClient.ClientInfo;
import ClientNetworking.GameClient.ClientInputThread;
import ClientNetworking.GameClient.ClientOutputThread;

import java.net.UnknownHostException;

public class DesktopLauncher {
	public static void main (String[] arg) {


        GameStateManager gameSimulation = new GameStateManager();
        gameSimulation.switchScene(GameStateManager.Scenes.TEST);

		//start gamethread
	    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new GameThread(gameSimulation), config);


		try {
			ClientInfo clientInfo = new ClientInfo();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		ClientOutputThread clientOutput = new ClientOutputThread();
		clientOutput.start();
		ClientInputThread clientInput = new ClientInputThread();
		clientInput.start();

	}
}
