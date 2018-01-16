package com.introfog.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.introfog.MyGame;
import com.introfog.mesh.objects.singletons.special.LevelManager;
import com.introfog.messages.PlayerLostMessage;

public class ShowError extends ScreenAdapter{
	private static boolean pushQuit = false;
	
	private WidgetGroup widgetGroup;
	private Stage stage;
	private Label name = new Label ("Имя ошибки.", TextStyle.getInstance ().labelStyle);
	private Label description = new Label ("Описание ошибки.", TextStyle.getInstance ().labelStyle);
	private TextButton quit = new TextButton ("Выйти", TextStyle.getInstance ().normalStyle);
	
	
	private void initializeNameLabel (){
		name.setPosition (MyGame.BUTTON_FONT_SIZE, Gdx.graphics.getHeight () / 2);
		widgetGroup.addActor (name);
	}
	
	private void initializeDescriptionLabel (){
		description.setPosition (MyGame.BUTTON_FONT_SIZE,
								 Gdx.graphics.getHeight () / 2 - MyGame.BUTTON_H - MyGame.DISTANCE_BETWEEN_BUTTONS);
		widgetGroup.addActor (description);
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
		initializeNameLabel ();
		initializeDescriptionLabel ();
		initializeQuitButton ();
		
		stage.addActor (widgetGroup);
	}
	
	private static class ShowErrorHolder{
		private final static ShowError instance = new ShowError ();
	}
	
	private ShowError (){
		widgetGroup = new WidgetGroup ();
		stage = new Stage (new ScreenViewport ());
		
		initializeButton ();
	}
	
	
	public static ShowError getInstance (){
		return ShowError.ShowErrorHolder.instance;
	}
	
	public void initialize (String name, String description){
		this.name.setText ("Ошибка: " + name);
		this.description.setText ("Описание: " + description);
		
		PlayerLostMessage plm = Pools.obtain (PlayerLostMessage.class);
		plm.initialize (LevelManager.getInstance ());
		LevelManager.getInstance ().sendMessage (plm);
		
		MyGame.getInstance ().setScreen (ShowError.getInstance ());
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