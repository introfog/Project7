package com.introfog.mesh.body;

import com.introfog.addition.math.Rectangle;

public class AnimatedObject extends Body{ //спец. класс для анимированных объектов, в котором хранятся координаты спрайта
	private float bodyShiftX;
	private Rectangle sprite;
	
	
	public AnimatedObject (float x, float y, float w, float h, float bodyW, float bodyH){
		bodyShiftX = (w - bodyW) / 2;
		sprite = new Rectangle (x, y, w, h);
		body = new Rectangle (x + bodyShiftX, y, bodyW, bodyH);
	}
	
	
	@Override
	public void setSpritePosition (float x, float y){
		sprite.setPosition (x, y);
		body.setPosition (x + bodyShiftX, y);
	}
	
	@Override
	public float getSpriteX (){
		return sprite.getX ();
	}
	
	@Override
	public float getSpriteY (){
		return sprite.getY ();
	}
	
	
	@Override
	public void setBodyPosition (float x, float y){
		sprite.setPosition (x - bodyShiftX, y);
		body.setPosition (x, y);
	}
	
	@Override
	public void move (float deltaX, float deltaY){
		body.move (deltaX, deltaY);
		sprite.move (deltaX, deltaY);
	}
}
