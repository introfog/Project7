package com.introfog.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.introfog.GameSystem;
import com.introfog.MyGame;

import com.introfog.mesh.objects.singletons.special.LevelManager;

public class SelectedModeScreen extends ScreenAdapter{
	private static boolean pushNewGame = false;
	private static boolean pushContinueGame = false;
	private static boolean pushBack = false;
	
	
	private WidgetGroup widgetGroup;
	private Stage stage;
	private TextButton newGame = new TextButton ("Новая игра", TextStyle.getInstance ().normalStyle);
	private TextButton continueGame = new TextButton ("Продолжить", TextStyle.getInstance ().normalStyle);
	private TextButton selectedLVL = new TextButton ("Уровни", TextStyle.getInstance ().normalStyle);
	private TextButton back = new TextButton ("Назад", TextStyle.getInstance ().normalStyle);
	
	
	private void initializeNewGameButton (){
		pushNewGame = false;
		ClickListener forNewGame = new ClickListener (){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button){
				if (!pushNewGame){
					if (GameSystem.IS_FIRST_GAME_START){
						LevelManager.getInstance ().newGame ();
						MyGame.getInstance ().setScreen (PlayScreen.getInstance ());
						pushNewGame = true;
					}
					else{
						MyGame.getInstance ().setScreen (QuestionNewGameScreen.getInstance ());
						pushNewGame = true;
					}
				}
			}
		};
		newGame.addListener (forNewGame);
		newGame.setBounds (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_W / 2,
				Gdx.graphics.getHeight () / 2 + 2 * MyGame.BUTTON_H + 2 * MyGame.DISTANCE_BETWEEN_BUTTONS, MyGame.BUTTON_W, MyGame.BUTTON_H);
		widgetGroup.addActor (newGame);
	}
	
	private void initializeContinueGameButton (){
		pushContinueGame = false;
		if (GameSystem.IS_FIRST_GAME_START){
			continueGame.setStyle (TextStyle.getInstance ().closedStyle);
		}
		ClickListener forContinueGame = new ClickListener (){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button){
				if (!GameSystem.IS_FIRST_GAME_START && !pushContinueGame){
					MyGame.getInstance ().setScreen (PlayScreen.getInstance ());
					pushContinueGame = true;
				}
			}
		};
		continueGame.addListener (forContinueGame);
		continueGame.setBounds (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_W / 2,
				Gdx.graphics.getHeight () / 2 + MyGame.BUTTON_H + MyGame.DISTANCE_BETWEEN_BUTTONS, MyGame.BUTTON_W,
				MyGame.BUTTON_H);
		widgetGroup.addActor (continueGame);
	}
	
	private void initializeSelectedLVLButton (){
		if (GameSystem.IS_FIRST_GAME_START){
			selectedLVL.setStyle (TextStyle.getInstance ().closedStyle);
		}
		selectedLVL.setBounds (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_W / 2, Gdx.graphics.getHeight () / 2,
				MyGame.BUTTON_W, MyGame.BUTTON_H);
		widgetGroup.addActor (selectedLVL);
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
		back.setBounds (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_W / 2, Gdx.graphics.getHeight () / 2 - MyGame.BUTTON_H - MyGame.DISTANCE_BETWEEN_BUTTONS, MyGame.BUTTON_W, MyGame.BUTTON_H);
		widgetGroup.addActor (back);
	}
	
	private void initializeButton (){
		initializeNewGameButton ();
		initializeContinueGameButton ();
		initializeSelectedLVLButton ();
		initializeBackButton ();
		
		stage.addActor (widgetGroup);
	}
	
	private static class SelectedModeScreenHolder{
		private final static SelectedModeScreen instance = new SelectedModeScreen ();
	}
	
	private SelectedModeScreen (){
		widgetGroup = new WidgetGroup ();
		stage = new Stage (new ScreenViewport ());
		
		initializeButton ();
	}
	
	
	public static SelectedModeScreen getInstance (){
		return SelectedModeScreenHolder.instance;
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
