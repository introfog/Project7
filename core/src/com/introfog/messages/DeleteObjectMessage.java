package com.introfog.messages;

import com.introfog.mesh.objects.GameObject;

public class DeleteObjectMessage extends GameMessage{
	public DeleteObjectMessage (GameObject object){
		this.type = MessageType.deleteObject;
		this.object = object;
		this.objectType = object.objectType;
	}
}
