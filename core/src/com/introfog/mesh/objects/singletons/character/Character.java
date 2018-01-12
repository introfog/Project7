package com.introfog.mesh.objects.singletons.character;

import com.introfog.mesh.body.AnimatedObject;
import com.introfog.mesh.objects.GameObject;
import com.introfog.mesh.objects.ObjectType;
import com.introfog.mesh.objects.State;
import com.introfog.mesh.objects.singletons.Camera;
import com.introfog.messages.*;

public class Character implements GameObject{
	protected static final float CHARACTER_W = UNIT;
	protected static final float CHARACTER_H = UNIT;
	protected static final float BODY_CHARACTER_W = 2 * CHARACTER_W / 5;
	protected static final float BODY_CHARACTER_H = CHARACTER_H / 4;
	
	protected CharacterName name = CharacterName.first;
	protected Direction currentDirection = Direction.forward;
	protected State state = State.stand;
	protected AnimatedObject body;
	
	private ObjectType objectType;
	private CharacterMessageParser parser;
	private CharacterControl control;
	private CharacterAnimations animations;
	
	
	private Character (CharacterName name){
		objectType = ObjectType.character;
		this.name = name;
		
		body = new AnimatedObject (0, 0, CHARACTER_W, CHARACTER_H, BODY_CHARACTER_W, BODY_CHARACTER_H);
		
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
	
	@Override
	public void update (){
		//именно animations первое
		
		
		control.update ();
		parser.update ();
		
	}
	
	@Override
	public boolean sendMessage (GameMessage message){
		return parser.parseMessage (message);
	}
	
	@Override
	public void draw (){
		animations.update ();
		animations.draw ();
		Camera.getInstance ().setPosition (body.getBodyX () + CHARACTER_W / 2, body.getBodyY () + CHARACTER_H / 2);
	}
	
	@Override
	public void clear (){
		state = State.stand;
		currentDirection = Direction.forward;
		
		animations.clear ();
	}
	
	@Override
	public ObjectType getObjectType (){
		return objectType;
	}
}