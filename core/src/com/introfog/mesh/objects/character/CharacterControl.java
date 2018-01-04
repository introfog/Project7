package com.introfog.mesh.objects.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.introfog.mesh.objects.State;
import com.introfog.mesh.objects.singletons.special.ObjectManager;
import com.introfog.messages.MoveMessage;

public class CharacterControl extends Character{
	protected static final float CHARACTER_SPEED = 80 * ASPECT_RATIO;
	
	protected float deltaX = 0;
	protected float deltaY = 0;
	protected Character character;
	
	
	protected void keyWPressed (){
		if (!Gdx.input.isKeyPressed (Input.Keys.S)){
			character.currentDirection = Direction.forward;
			deltaY = CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
		}
	}
	
	protected void keySPressed (){
		if (!Gdx.input.isKeyPressed (Input.Keys.W)){
			character.currentDirection = Direction.back;
			deltaY = -CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
		}
	}
	
	protected void keyDPressed (){
		if (!Gdx.input.isKeyPressed (Input.Keys.A)){
			if (!Gdx.input.isKeyPressed (Input.Keys.W)){
				character.currentDirection = Direction.right;
			}
			deltaX = CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
		}
	}
	
	protected void keyAPressed (){
		if (!Gdx.input.isKeyPressed (Input.Keys.D)){
			if (!Gdx.input.isKeyPressed (Input.Keys.W)){
				character.currentDirection = Direction.left;
			}
			deltaX = -CHARACTER_SPEED * Gdx.graphics.getDeltaTime ();
		}
	}
	
	private void updateMoveControl (){
		character.state = State.stand;
		deltaX = 0;
		deltaY = 0;
		if (Gdx.input.isKeyPressed (Input.Keys.W)){
			keyWPressed ();
		}
		if (Gdx.input.isKeyPressed (Input.Keys.S)){
			keySPressed ();
		}
		if (Gdx.input.isKeyPressed (Input.Keys.D)){
			keyDPressed ();
		}
		if (Gdx.input.isKeyPressed (Input.Keys.A)){
			keyAPressed ();
		}
		
		
		if (deltaX != 0){
			character.state = State.move;
			ObjectManager.getInstance ().addMessage (
					new MoveMessage (character, deltaX, 0, character.getBodyX (), character.getBodyY (),
									 character.getSpriteX (), character.getSpriteY (), character.getBodyW (),
									 character.getBodyH ()));
			character.move (deltaX, 0);
		}
		if (deltaY != 0){
			character.state = State.move;
			ObjectManager.getInstance ().addMessage (
					new MoveMessage (character, 0, deltaY, character.getBodyX (), character.getBodyY (),
									 character.getSpriteX (), character.getSpriteY (), character.getBodyW (),
									 character.getBodyH ()));
			character.move (0, deltaY);
		}
	}
	
	
	public CharacterControl (Character character){
		this.character = character;
	}
	
	@Override
	public void update (){
		if (character.state == State.stand || character.state == State.move){
			updateMoveControl ();
		}
	}
}