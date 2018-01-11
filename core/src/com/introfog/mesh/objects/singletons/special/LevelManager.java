package com.introfog.mesh.objects.singletons.special;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.badlogic.gdx.utils.Pools;
import com.introfog.GameSystem;
import com.introfog.MyGame;
import com.introfog.addition.parsers.ParseLevel;
import com.introfog.addition.parsers.ParseSettings;
import com.introfog.mesh.objects.GameObject;
import com.introfog.messages.*;
import com.introfog.screens.SelectedModeScreen;

public class LevelManager implements GameObject{
	public static float NUM_TILE_W;
	public static float NUM_TILE_H;
	
	
	private void completeLevel (){
		ObjectManager.getInstance ().clear ();
		
		if (GameSystem.CURRENT_LEVEL != GameSystem.NUM_LEVELS){
			GameSystem.CURRENT_LEVEL++;
		}
		else{
			GameSystem.GAME_OVER = true;
		}
		
		if (GameSystem.NUM_PASSED_LEVELS != GameSystem.NUM_LEVELS){
			GameSystem.NUM_PASSED_LEVELS++;
		}
		ParseSettings.writeSettings ();
		
		MyGame.getInstance ().setScreen (SelectedModeScreen.getInstance ());
	}
	
	private void closeLevel (){
		ObjectManager.getInstance ().clear ();
		MyGame.getInstance ().setScreen (SelectedModeScreen.getInstance ());
	}
	
	private static class LevelManagerHolder{
		private final static LevelManager instance = new LevelManager ();
	}
	
	private LevelManager (){ }
	
	
	public static LevelManager getInstance (){
		return LevelManagerHolder.instance;
	}
	
	public void newGame (){
		GameSystem.IS_FIRST_GAME_START = true;
		GameSystem.CURRENT_LEVEL = 1;
		GameSystem.NUM_PASSED_LEVELS = 0;
		GameSystem.GAME_OVER = false;
		
		ParseSettings.writeSettings ();
	}
	
	public void createLevel (){
		ParseLevel.parseLVL (GameSystem.CURRENT_LEVEL);
		
		AddObjectMessage aom = Pools.obtain (AddObjectMessage.class);
		aom.initialize (this);
		ObjectManager.getInstance ().sendMessage (aom);
		
		if (GameSystem.IS_FIRST_GAME_START){
			GameSystem.IS_FIRST_GAME_START = false;
			ParseSettings.writeSettings ();
		}
	}
	
	public void updateLevel (){
		ObjectManager.getInstance ().update ();
		ObjectManager.getInstance ().draw ();
	}
	
	@Override
	public void update (){
		if (Gdx.input.isKeyJustPressed (Input.Keys.ESCAPE)){
			closeLevel ();
		}
	}
	
	@Override
	public boolean sendMessage (GameMessage message){
		if (message.type == MessageType.levelComplete){
			completeLevel ();
			return true;
		}
		else if (message.type == MessageType.playerLost){
			closeLevel ();
			return true;
		}
		return false;
	}
}