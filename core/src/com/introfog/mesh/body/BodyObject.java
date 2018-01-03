package com.introfog.mesh.body;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import com.introfog.addition.math.Rectangle;

public class BodyObject extends Body{
	private float bodyShiftX;
	
	
	public BodyObject (String fileName, float x, float y, float w, float h, float bodyW, float bodyH){
		Texture texture = new Texture (fileName);
		sprite = new Sprite (texture);
		sprite.setBounds (x, y, w, h);
		bodyShiftX = (w - bodyW) / 2;
		body = new Rectangle (x + bodyShiftX, y, bodyW, bodyH);
	}
	
	@Override
	public void setSpritePosition (float x, float y){
		sprite.setPosition (x, y);
		body.setPosition (x + bodyShiftX, y);
	}
}
