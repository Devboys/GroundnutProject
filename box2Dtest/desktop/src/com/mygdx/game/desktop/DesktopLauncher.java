package com.mygdx.game.desktop;

import Constants.ScreenConstants;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = ScreenConstants.CAM_HEIGHT;
		config.width = ScreenConstants.CAM_WIDTH;
		new LwjglApplication(new MyGdxGame(), config);
	}
}
