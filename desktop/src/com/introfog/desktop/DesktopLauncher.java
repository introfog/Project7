package com.introfog.desktop;

import com.introfog.GameSystem;
import com.introfog.MyGame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher{
	public static void main (String[] arg){
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration ();
		
		config.title = "Project 7";
		config.fullscreen = GameSystem.FULL_SCREEN;
		config.width = (int) GameSystem.SCREEN_W;
		config.height = (int) GameSystem.SCREEN_H;
		
		new LwjglApplication (MyGame.getInstance (), config);
	}
}
