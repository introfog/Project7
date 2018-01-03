package com.introfog.mesh.objects.box;

import com.badlogic.gdx.utils.Pools;

import com.introfog.mesh.body.AnimatedObject;
import com.introfog.mesh.objects.GameObject;
import com.introfog.mesh.objects.ObjectType;
import com.introfog.mesh.objects.State;
import com.introfog.messages.*;

public class Box extends GameObject{
	protected static final float BODY_BOX_W = UNIT - 1;
	protected static final float BODY_BOX_H = UNIT * ANGLE - 1;
	protected static final float BOX_W = UNIT;
	protected static final float BOX_H = UNIT + UNIT * ANGLE;
	
	protected State state = State.stand;
	
	private BoxMessageParser parser;
	private BoxAnimations animations;
	
	
	public Box (){ }
	
	public Box (boolean fictiv){ //нужен фиктивный параметр, что бы не зацикливалось создание ящика.
		objectType = ObjectType.box;
		body = new AnimatedObject (0, 0, BOX_W, BOX_H, BODY_BOX_W, BODY_BOX_H);
		body.move (0, 0.5f);
		
		parser = new BoxMessageParser (this);
		animations = new BoxAnimations (this);
	}
	
	public void setSpritePosition (float x, float y){
		body.setSpritePosition (x, y);
	}
	
	@Override
	public void update (){
		parser.update ();
		animations.update ();
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