package com.introfog.messages;

import com.introfog.mesh.objects.GameObject;

public class MoveMessage extends GameMessage{
	public float deltaX;
	public float deltaY;
	public float spriteOldX;
	public float spriteOldY;
	public float oldBodyX;
	public float oldBodyY;
	public float bodyW;
	public float bodyH;
	
	
	public void initialize (GameObject object, float deltaX, float deltaY, float oldBodyX, float oldBodyY,
							float spriteOldX, float spriteOldY, float bodyW, float bodyH){
		this.type = MessageType.move;
		this.object = object;
		this.objectType = object.getObjectType ();
		
		this.deltaX = deltaX;
		this.deltaY = deltaY;
		this.spriteOldX = spriteOldX;
		this.spriteOldY = spriteOldY;
		this.oldBodyX = oldBodyX;
		this.oldBodyY = oldBodyY;
		this.bodyW = bodyW;
		this.bodyH = bodyH;
	}
}
