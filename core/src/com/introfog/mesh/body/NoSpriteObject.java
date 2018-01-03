package com.introfog.mesh.body;

import com.introfog.addition.math.Rectangle;

public class NoSpriteObject extends Body{
	public NoSpriteObject (float x, float y, float bodyW, float bodyH){
		body = new Rectangle (x, y, bodyW, bodyH);
	}
}
