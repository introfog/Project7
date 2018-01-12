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

public class MainMenuScreen extends ScreenAdapter{
	private static boolean pushPlay = false;
	private static boolean pushSettings = false;
	private static boolean pushExit = false;
	
	private WidgetGroup widgetGroup;
	private Stage stage;
	private TextButton play = new TextButton ("Играть", TextStyle.getInstance ().normalStyle);
	private TextButton settings = new TextButton ("Настройки", TextStyle.getInstance ().normalStyle);
	private TextButton exit = new TextButton ("Выход", TextStyle.getInstance ().normalStyle);
	
	
	private void initializePlayButton (){
		pushPlay = false;
		play.addListener (new ClickListener (){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button){
				if (!pushPlay){
					MyGame.getInstance ().setScreen (SelectedModeScreen.getInstance ());
					pushPlay = true;
				}
			}
		});
		play.setBounds (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_W / 2,
						Gdx.graphics.getHeight () / 2 + MyGame.BUTTON_H + MyGame.DISTANCE_BETWEEN_BUTTONS,
						MyGame.BUTTON_W, MyGame.BUTTON_H);
		widgetGroup.addActor (play);
	}
	
	private void initializeSettingsButton (){
		pushSettings = false;
		ClickListener forSettings = new ClickListener (){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button){
				if (!pushSettings){
					MyGame.getInstance ().setScreen (SettingsScreen.getInstance ());
					pushSettings = true;
				}
			}
		};
		settings.addListener (forSettings);
		settings.setBounds (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_W / 2,
				Gdx.graphics.getHeight () / 2, MyGame.BUTTON_W, MyGame.BUTTON_H);
		widgetGroup.addActor (settings);
	}
	
	private void initializeExitButton (){
		pushExit = false;
		ClickListener forExit = new ClickListener (){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button){
				if (!pushExit){
					MyGame.getInstance ().setScreen (QuitGameScreen.getInstance ());
					pushExit = true;
				}
			}
		};
		exit.addListener (forExit);
		exit.setBounds (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_W / 2,
				Gdx.graphics.getHeight () / 2 - MyGame.BUTTON_H - MyGame.DISTANCE_BETWEEN_BUTTONS,
				MyGame.BUTTON_W, MyGame.BUTTON_H);
		widgetGroup.addActor (exit);
	}
	
	private void initializeButton (){
		initializePlayButton ();
		initializeSettingsButton ();
		initializeExitButton ();
		
		stage.addActor (widgetGroup);
	}
	
	private static class MainMenuScreenHolder{
		private final static MainMenuScreen instance = new MainMenuScreen ();
	}
	
	private MainMenuScreen (){
		widgetGroup = new WidgetGroup ();
		stage = new Stage (new ScreenViewport ());
		
		initializeButton ();
	}
	
	
	public static MainMenuScreen getInstance (){
		return MainMenuScreenHolder.instance;
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
		
		stage.act (delta);
		stage.draw ();
	}
}