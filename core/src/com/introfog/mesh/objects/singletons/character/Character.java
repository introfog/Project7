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
	
	protected NatureType natureType = NatureType.summer;
	protected Direction currentDirection = Direction.forward;
	protected State state = State.stand;
	protected AnimatedObject body;
	
	private ObjectType objectType;
	private CharacterMessageParser parser;
	private CharacterControl control;
	private CharacterAnimations animationsSummer;
	private CharacterAnimations animationsWinter;
	
	
	private Character (NatureType natureType){
		objectType = ObjectType.character;
		this.natureType = natureType;
		
		body = new AnimatedObject (0, 0, CHARACTER_W, CHARACTER_H, BODY_CHARACTER_W, BODY_CHARACTER_H);
		
		parser = new CharacterMessageParser (this);
		control = new CharacterControl (this);
		animationsSummer = new CharacterAnimations (this, NatureType.summer);
		animationsWinter = new CharacterAnimations (this, NatureType.winter);
	}
	
	private static class CharacterHolder{
		private final static Character summer = new Character (NatureType.summer);
	}
	
	
	protected Character (){ }
	
	
	public static Character getInstance (){
		return CharacterHolder.summer;
	}
	
	public void setSpritePosition (float x, float y){
		body.setSpritePosition (x, y);
		body.move (0, 0.25f);
		Camera.getInstance ().setPosition (x + CHARACTER_W / 2, y + CHARACTER_H / 2);
	}
	
	public float getTopSpriteY (){
		return body.getSpriteY () - GameObject.UNIT / 2;
	}
	
	public float getCenterSpriteX (){
		return body.getSpriteX () + GameObject.UNIT / 2;
	}
	
	@Override
	public void update (){
		control.update ();
		parser.update ();
	}
	
	@Override
	public boolean sendMessage (GameMessage message){
		return parser.parseMessage (message);
	}
	
	@Override
	public void draw (){
		if (natureType == NatureType.summer){
			animationsSummer.update ();
			animationsSummer.draw ();
		}
		else{
			animationsWinter.update ();
			animationsWinter.draw ();
		}
		Camera.getInstance ().setPosition (body.getBodyX () + CHARACTER_W / 2, body.getBodyY () + CHARACTER_H / 2);
	}
	
	@Override
	public void clear (){
		state = State.stand;
		currentDirection = Direction.forward;
		
		animationsSummer.clear ();
		animationsWinter.clear ();
	}
	
	@Override
	public ObjectType getObjectType (){
		return objectType;
	}
	
	@Override
	public NatureType getNatureType (){
		return natureType;
	}
}