package com.introfog.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.introfog.MyGame;

public class QuitGameScreen extends ScreenAdapter{
	private static boolean pushReturnn = false;
	private static boolean pushQuit = false;
	
	private WidgetGroup widgetGroup;
	private Stage stage;
	private Label question = new Label ("Вы действительно хотите выйти?", TextStyle.getInstance ().labelStyle);
	private TextButton returnn = new TextButton ("Вернуться", TextStyle.getInstance ().normalStyle);
	private TextButton quit = new TextButton ("Выйти", TextStyle.getInstance ().normalStyle);
	
	
	private void initializeQuestionLabel (){
		question.setPosition (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_FONT_SIZE * 8,
				Gdx.graphics.getHeight () / 2);
		widgetGroup.addActor (question);
	}
	
	private void initializeReturnButton (){
		pushReturnn = false;
		ClickListener forReturnn = new ClickListener (){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button){
				if (!pushReturnn){
					MyGame.getInstance ().setScreen (MainMenuScreen.getInstance ());
					pushReturnn = true;
				}
			}
		};
		returnn.addListener (forReturnn);
		returnn.setBounds (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_W / 2,
				Gdx.graphics.getHeight () / 2 - MyGame.BUTTON_H - MyGame.DISTANCE_BETWEEN_BUTTONS,
				MyGame.BUTTON_W, MyGame.BUTTON_H);
		widgetGroup.addActor (returnn);
	}
	
	private void initializeQuitButton (){
		pushQuit = false;
		ClickListener forQuit = new ClickListener (){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button){
				if (!pushQuit){
					Gdx.app.exit ();
					pushQuit = true;
				}
			}
		};
		quit.addListener (forQuit);
		quit.setBounds (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_W / 2,
				Gdx.graphics.getHeight () / 2 - 2 * (MyGame.BUTTON_H + MyGame.DISTANCE_BETWEEN_BUTTONS),
				MyGame.BUTTON_W, MyGame.BUTTON_H);
		widgetGroup.addActor (quit);
	}
	
	private void initializeButton (){
		initializeQuestionLabel ();
		initializeReturnButton ();
		initializeQuitButton ();
		
		stage.addActor (widgetGroup);
	}
	
	private static class QuitGameScreenHolder{
		private final static QuitGameScreen instance = new QuitGameScreen ();
	}
	
	private QuitGameScreen (){
		widgetGroup = new WidgetGroup ();
		stage = new Stage (new ScreenViewport ());
		
		initializeButton ();
	}
	
	
	public static QuitGameScreen getInstance (){
		return QuitGameScreenHolder.instance;
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