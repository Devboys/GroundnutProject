package com.groudnut.client.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.groudnut.client.ClientGraphics;
import com.groudnut.client.ClientInputThread;
import com.groudnut.client.ClientOutputThread;
import com.groudnut.server.NetworkHandler;
import com.groudnut.server.ServerInputThread;
import com.groudnut.server.ServerOutputThread;


public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new ClientGraphics(), config);

		NetworkHandler networkHandler = new NetworkHandler();

		ServerInputThread serverInput = new ServerInputThread();
		ServerOutputThread serverOutput = new ServerOutputThread();
		serverInput.start();
		serverOutput.start();

		ClientInputThread clientInput = new ClientInputThread();
		ClientOutputThread clientOutput = new ClientOutputThread();
		clientInput.start();
		clientOutput.start();
	}
}
