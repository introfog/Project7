package com.introfog.messages;

import com.introfog.mesh.objects.GameObject;

public class AddObjectMessage extends GameMessage{
	public void initialize (GameObject object){
		this.type = MessageType.addObject;
		this.object = object;
		this.objectType = object.getObjectType ();
	}
}
