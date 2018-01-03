package com.introfog.mesh.objects.character;

import com.badlogic.gdx.utils.Pools;

import com.introfog.mesh.body.AnimatedObject;
import com.introfog.mesh.objects.GameObject;
import com.introfog.mesh.objects.ObjectType;
import com.introfog.mesh.objects.State;
import com.introfog.messages.GameMessage;

public class Character extends GameObject{
	protected static final float CHARACTER_W = UNIT;
	protected static final float CHARACTER_H = UNIT;
	
	private static final float BODY_CHARACTER_W = 2 * CHARACTER_W / 5;
	private static final float BODY_CHARACTER_H = CHARACTER_H / 4;
	
	protected boolean goToObject = false;
	protected boolean isSelected = false;
	protected Direction currentDirection = Direction.forward;
	protected State state = State.stand;
	
	private CharacterName name = CharacterName.unknown;
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
		private final static Character second = new Character (CharacterName.second);
	}
	
	protected Character (){ }
	
	
	public static Character getFirstInstance (){
		return CharacterHolder.first;
	}
	
	public static Character getSecondInstance (){
		return CharacterHolder.second;
	}
	
	
	public void setSpritePosition (float x, float y){
		isSelected = (name == CharacterName.first);
		
		body.setSpritePosition (x, y);
		body.move (0, 0.25f);
	}
	
	public CharacterName getName (){
		return name;
	}
	
	@Override
	public void update (){
		parser.update ();
		animations.update ();
		control.update ();
	}
	
	@Override
	public void sendMessage (GameMessage message){
		parser.parseMessage (message);
	}
	
	@Override
	public void draw (){
		animations.draw ();
	}
	
	@Override
	public void clear (){
		state = State.stand;
		currentDirection = Direction.forward;
		
		control.clear ();
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
	
	protected float getSpriteW (){
		return body.getSpriteW ();
	}
	
	protected float getSpriteH (){
		return body.getSpriteH ();
	}
	
	protected void move (float deltaX, float deltaY){
		body.move (deltaX, deltaY);
	}
	
	protected boolean intersects (float x, float y, float w, float h){
		return body.intersects (x, y, w, h);
	}
}