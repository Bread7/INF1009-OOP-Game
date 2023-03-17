package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.MyGdxGame;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(MyGdxGame.APP_DESKTOP_FPS);
		config.setTitle(MyGdxGame.APP_TITLE + " v" + MyGdxGame.APP_VERSION);
		config.setWindowedMode(MyGdxGame.APP_DESKTOP_WIDTH, MyGdxGame.APP_DESKTOP_HEIGHT);

		new Lwjgl3Application(new MyGdxGame(), config);
	}
}
