package com.introfog.mesh.objects.character;

import com.badlogic.gdx.utils.Pools;

import com.introfog.mesh.body.AnimatedObject;
import com.introfog.mesh.objects.GameObject;
import com.introfog.mesh.objects.ObjectType;
import com.introfog.mesh.objects.State;
import com.introfog.mesh.objects.singletons.Camera;
import com.introfog.messages.*;

import java.util.LinkedList;

public class Character extends GameObject{
	protected static final float CHARACTER_W = UNIT;
	protected static final float CHARACTER_H = UNIT;
	
	private static final float BODY_CHARACTER_W = 2 * CHARACTER_W / 5;
	private static final float BODY_CHARACTER_H = CHARACTER_H / 4;
	
	protected Direction currentDirection = Direction.forward;
	protected State state = State.stand;
	
	private CharacterName name = CharacterName.first;
	private CharacterMessageParser parser;
	private CharacterControl control;
	private CharacterAnimations animations;
	
	
	private Character (CharacterName name){
		objectType = ObjectType.character;
		this.name = name;
		
		body = new AnimatedObject (0, 0, CHARACTER_W, CHARACTER_H, BODY_CHARACTER_W, BODY_CHARACTER_H);
		body.move (0, 0.25f);
		
		parser = new CharacterMessageParser (this);
		control = new CharacterControl (this);
		animations = new CharacterAnimations (this);
	}
	
	private static class CharacterHolder{
		private final static Character first = new Character (CharacterName.first);
	}
	
	
	protected Character (){ }
	
	
	public static Character getInstance (){
		return CharacterHolder.first;
	}
	
	public void setSpritePosition (float x, float y){
		body.setSpritePosition (x, y);
		body.move (0, 0.25f);
		Camera.getInstance ().setPosition (x + CHARACTER_W / 2, y + CHARACTER_H / 2);
	}
	
	public CharacterName getName (){
		return name;
	}
	
	@Override
	public void update (){
		animations.update ();
		control.update ();
	}
	
	@Override
	public boolean sendMessage (GameMessage message){
		return parser.parseMessage (message);
	}
	
	@Override
	public void draw (){
		animations.draw ();
		Camera.getInstance ().setPosition (getBodyX () + CHARACTER_W / 2, getBodyY () + CHARACTER_H / 2);
	}
	
	@Override
	public void clear (){
		state = State.stand;
		currentDirection = Direction.forward;
		
		animations.clear ();
		Pools.free (this);
	}
	
	
	protected float getBodyX (){
		return body.getBodyX ();
	}
	
	protected float getBodyY (){
		return body.getBodyY ();
	}
	
	protected float getBodyW (){
		return body.getBodyW ();
	}
	
	protected float getBodyH (){
		return body.getBodyH ();
	}
	
	protected float getSpriteX (){
		return body.getSpriteX ();
	}
	
	protected float getSpriteY (){
		return body.getSpriteY ();
	}
	
	protected void move (float deltaX, float deltaY){
		body.move (deltaX, deltaY);
	}
	
	protected boolean intersects (float x, float y, float w, float h){
		return body.intersects (x, y, w, h);
	}
}