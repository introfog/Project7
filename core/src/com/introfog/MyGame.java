package com.introfog;

import com.badlogic.gdx.Game;

import com.introfog.addition.parsers.*;
import com.introfog.mesh.objects.GameObject;
import com.introfog.screens.*;

public class MyGame extends Game{
	public static final float BUTTON_W = 250 * GameObject.ASPECT_RATIO;
	public static final float BUTTON_H = 55 * GameObject.ASPECT_RATIO;
	public static final float DISTANCE_BETWEEN_BUTTONS = 15 * GameObject.ASPECT_RATIO;
	public static final int   BUTTON_FONT_SIZE = (int) (3 * BUTTON_H / 5);
	
	
	private static class MyGameHolder{
		private final static MyGame instance = new MyGame ();
	}
	
	private MyGame (){ }
	
	
	public static MyGame getInstance (){
		return MyGameHolder.instance;
	}
	
	@Override
	public void create (){
		ParseBasis.findAbsolutePath ();
		ParseSettings.parseSettings ();
		if (this.screen != ShowError.getInstance ()){
			setScreen (MainMenuScreen.getInstance ());
		}
	}
}