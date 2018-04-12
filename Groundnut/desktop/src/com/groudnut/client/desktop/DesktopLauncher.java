package com.groudnut.client.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.groudnut.client.*;
import com.groudnut.server.ServerHandler;
import com.groudnut.server.ServerInputThread;
import com.groudnut.server.ServerOutputThread;

import java.io.IOException;
import java.net.UnknownHostException;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new ClientGraphics(), config);

		ClientInputThread clientInput = new ClientInputThread();
		ClientOutputThread clientOutput = new ClientOutputThread();
		clientInput.start();
		clientOutput.start();

		LobbyClientMain lcm = new LobbyClientMain();

	}
}