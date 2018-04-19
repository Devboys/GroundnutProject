package Desktop;

<<<<<<< HEAD
import Core.RenderThread;
import Input.PlayerInputHandler;
=======
import Core.GameThread;
import Scenes.GameStateManager;
import Scenes.Scene;
>>>>>>> 1fafd57bb3d6b3cd8a960d1a25cef22bc0f69480
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ClientNetworking.GameClient.ClientInfo;
import ClientNetworking.GameClient.ClientInputThread;
import ClientNetworking.GameClient.ClientOutputThread;

import java.net.UnknownHostException;

public class DesktopLauncher {
	public static void main (String[] arg) {
<<<<<<< HEAD
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new RenderThread(), config);

		ClientInfo clientInfo = new ClientInfo();
=======


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
>>>>>>> 1fafd57bb3d6b3cd8a960d1a25cef22bc0f69480

		ClientOutputThread clientOutput = new ClientOutputThread();
		clientOutput.start();
		ClientInputThread clientInput = new ClientInputThread();
		clientInput.start();

	}
}
