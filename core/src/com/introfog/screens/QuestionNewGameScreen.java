package com.introfog.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.introfog.MyGame;
import com.introfog.mesh.objects.singletons.special.LevelManager;

public class QuestionNewGameScreen extends ScreenAdapter{
	private static boolean pushReturnn = false;
	private static boolean pushCreate = false;
	
	private WidgetGroup widgetGroup;
	private Stage stage;
	private Label question = new Label ("Начать новую игру?", TextStyle.getInstance ().labelStyle);
	private TextButton returnn = new TextButton ("Вернуться", TextStyle.getInstance ().normalStyle);
	private TextButton create = new TextButton ("Создать", TextStyle.getInstance ().normalStyle);
	
	
	private void initializeQuestionLabel (){
		question.setPosition (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_FONT_SIZE * 5,
							  Gdx.graphics.getHeight () / 2 + 2 * MyGame.BUTTON_H + 2 * MyGame.DISTANCE_BETWEEN_BUTTONS);
		widgetGroup.addActor (question);
	}
	
	private void initializeReturnButton (){
		pushReturnn = false;
		ClickListener forReturnn = new ClickListener (){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button){
				if (!pushReturnn){
					MyGame.getInstance ().setScreen (SelectedModeScreen.getInstance ());
					pushReturnn = true;
				}
			}
		};
		returnn.addListener (forReturnn);
		returnn.setBounds (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_W / 2,
						   Gdx.graphics.getHeight () / 2 + MyGame.BUTTON_H + MyGame.DISTANCE_BETWEEN_BUTTONS, MyGame.BUTTON_W,
						   MyGame.BUTTON_H);
		widgetGroup.addActor (returnn);
	}
	
	private void initializeCreateButton (){
		pushCreate = false;
		ClickListener forCreate = new ClickListener (){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button){
				if (!pushCreate){
					LevelManager.getInstance ().newGame ();
					MyGame.getInstance ().setScreen (PlayScreen.getInstance ());
					pushCreate = true;
				}
			}
		};
		create.addListener (forCreate);
		create.setBounds (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_W / 2, Gdx.graphics.getHeight () / 2,
						  MyGame.BUTTON_W, MyGame.BUTTON_H);
		widgetGroup.addActor (create);
	}
	
	private void initializeButton (){
		initializeQuestionLabel ();
		initializeReturnButton ();
		initializeCreateButton ();
		
		stage.addActor (widgetGroup);
	}
	
	private static class QuestionNewGameScreenHolder{
		private final static QuestionNewGameScreen instance = new QuestionNewGameScreen ();
	}
	
	private QuestionNewGameScreen (){
		widgetGroup = new WidgetGroup ();
		stage = new Stage (new ScreenViewport ());
		
		initializeButton ();
	}
	
	
	public static QuestionNewGameScreen getInstance (){
		return QuestionNewGameScreen.QuestionNewGameScreenHolder.instance;
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
			MyGame.getInstance ().setScreen (SelectedModeScreen.getInstance ());
		}
		
		stage.act (delta);
		stage.draw ();
	}
}
