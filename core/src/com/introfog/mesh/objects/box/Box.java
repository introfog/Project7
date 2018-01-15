package com.introfog.mesh.objects.box;

import com.badlogic.gdx.utils.Pools;

import com.introfog.mesh.body.AnimatedObject;
import com.introfog.mesh.objects.GameObject;
import com.introfog.mesh.objects.ObjectType;
import com.introfog.mesh.objects.State;
import com.introfog.mesh.objects.singletons.character.CharacterName;
import com.introfog.messages.*;

public class Box implements GameObject{
	protected static final float BODY_BOX_W = UNIT - 1;
	protected static final float BODY_BOX_H = UNIT * ANGLE - 1;
	protected static final float BOX_W = UNIT;
	protected static final float BOX_H = UNIT + UNIT * ANGLE;
	
	protected CharacterName type = CharacterName.summer;
	protected State state = State.stand;
	protected AnimatedObject body;
	
	private ObjectType objectType;
	private BoxMessageParser parser;
	private BoxAnimations animations;
	
	
	public Box (){ }
	
	public Box (boolean fictiv){ //нужен фиктивный параметр, что бы не зацикливалось создание ящика.
		objectType = ObjectType.box;
		body = new AnimatedObject (0, 0, BOX_W, BOX_H, BODY_BOX_W, BODY_BOX_H);
		
		parser = new BoxMessageParser (this);
		animations = new BoxAnimations (this);
	}
	
	public void setSpritePosition (float x, float y, CharacterName type){
		this.type = type;
		body.setSpritePosition (x, y);
		body.move (0, 0.5f);
	}
	
	@Override
	public void update (){
		animations.update ();
	}
	
	@Override
	public boolean sendMessage (GameMessage message){
		return parser.parseMessage (message);
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
	
	@Override
	public ObjectType getObjectType (){
		return objectType;
	}
}