package com.introfog.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.introfog.MyGame;

public class SettingsScreen extends ScreenAdapter{
	private static boolean pushBack = false;
	
	private WidgetGroup widgetGroup;
	private Stage stage;
	private TextButton video = new TextButton ("Видео", TextStyle.getInstance ().normalStyle);
	private TextButton control = new TextButton ("Управление", TextStyle.getInstance ().normalStyle);
	private TextButton sound = new TextButton ("Звук", TextStyle.getInstance ().normalStyle);
	private TextButton back = new TextButton ("Назад", TextStyle.getInstance ().normalStyle);
	
	
	private void initializeVideoButton (){
		video.setBounds (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_W / 2,
				Gdx.graphics.getHeight () / 2 + 2 * MyGame.BUTTON_H + 2 * MyGame.DISTANCE_BETWEEN_BUTTONS,
				MyGame.BUTTON_W, MyGame.BUTTON_H);
		widgetGroup.addActor (video);
	}
	
	private void initializeControlButton (){
		control.setBounds (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_W / 2,
				Gdx.graphics.getHeight () / 2 + MyGame.BUTTON_H + MyGame.DISTANCE_BETWEEN_BUTTONS,
				MyGame.BUTTON_W, MyGame.BUTTON_H);
		widgetGroup.addActor (control);
	}
	
	private void initializeSoundButton (){
		sound.setBounds (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_W / 2,
				Gdx.graphics.getHeight () / 2,
				MyGame.BUTTON_W, MyGame.BUTTON_H);
		widgetGroup.addActor (sound);
	}
	
	private void initializeBackButton (){
		pushBack = false;
		ClickListener forBack = new ClickListener (){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button){
				if (!pushBack){
					MyGame.getInstance ().setScreen (MainMenuScreen.getInstance ());
					pushBack = true;
				}
			}
		};
		back.addListener (forBack);
		back.setBounds (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_W / 2,
				Gdx.graphics.getHeight () / 2 - MyGame.BUTTON_H - MyGame.DISTANCE_BETWEEN_BUTTONS,
				MyGame.BUTTON_W, MyGame.BUTTON_H);
		widgetGroup.addActor (back);
	}
	
	private void initializeButton (){
		initializeVideoButton ();
		initializeControlButton ();
		initializeSoundButton ();
		initializeBackButton ();
		
		stage.addActor (widgetGroup);
	}
	
	
	private static class SettingsScreenHolder{
		private final static SettingsScreen instance = new SettingsScreen ();
	}
	
	private SettingsScreen (){
		widgetGroup = new WidgetGroup ();
		stage = new Stage (new ScreenViewport ());
		
		initializeButton ();
	}
	
	
	public static SettingsScreen getInstance (){
		return SettingsScreenHolder.instance;
	}
	
	@Override
	public void show (){
		initializeButton ();
		
		// Устанавливаем нашу сцену основным процессором для ввода
		Gdx.input.setInputProcessor (stage);
	}
	
	@Override
	public void render (float delta){
		Gdx.gl.glClearColor (0, 0, 0, 1);
		Gdx.gl.glClear (GL20.GL_COLOR_BUFFER_BIT);
		
		if (Gdx.input.isKeyJustPressed (Input.Keys.ESCAPE)){
			MyGame.getInstance ().setScreen (MainMenuScreen.getInstance ());
		}
		
		stage.act (delta);
		stage.draw ();
	}
}
