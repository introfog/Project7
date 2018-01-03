package com.introfog.messages;

import com.introfog.mesh.objects.GameObject;

public class PushOutMessage extends GameMessage{
	public float deltaX;
	public float deltaY;
	
	
	public PushOutMessage (GameObject object, float deltaX, float deltaY){
		this.type = MessageType.pushOut;
		this.object = object;
		this.objectType = object.objectType;
		
		this.deltaX = deltaX;
		this.deltaY = deltaY;
	}
}
