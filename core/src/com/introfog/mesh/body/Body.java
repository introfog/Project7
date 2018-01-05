package com.introfog.mesh.body;

import com.badlogic.gdx.graphics.g2d.Sprite;

import com.introfog.addition.math.Rectangle;

public abstract class Body{
	protected Rectangle body;
	protected Sprite sprite;
	
	
	public void move (float deltaX, float deltaY){
		sprite.setPosition (sprite.getX () + deltaX, sprite.getY () + deltaY);
		body.move (deltaX, deltaY);
	}
	
	public final boolean intersects (float x, float y, float w, float h){
		return body.intersects (x, y, w, h);
	}
	
	
	public float getBodyX (){
		return body.getX ();
	}
	
	public float getBodyY (){
		return body.getY ();
	}
	
	public float getBodyW (){
		return body.getW ();
	}
	
	public float getBodyH (){
		return body.getH ();
	}
	
	
	public float getSpriteX (){
		return sprite.getX ();
	}
	
	public float getSpriteY (){
		return sprite.getY ();
	}
	
	public void setSpritePosition (float x, float y){
		sprite.setPosition (x, y);
		body.setPosition (x, y);
	}
	
	public void setBodyPosition (float x, float y){
		sprite.setPosition (x, y);
		body.setPosition (x, y);
	}
}