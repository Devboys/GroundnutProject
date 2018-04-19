package Desktop;

import Core.GameThread;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ClientNetworking.GameClient.ClientInputThread;
import ClientNetworking.GameClient.ClientOutputThread;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new GameThread(), config);

		ClientOutputThread clientOutput = new ClientOutputThread();
		clientOutput.start();
		ClientInputThread clientInput = new ClientInputThread();
		clientInput.start();

	}
}
