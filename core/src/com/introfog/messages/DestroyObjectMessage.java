package com.introfog.messages;

import com.introfog.mesh.objects.*;

public class DestroyObjectMessage extends GameMessage{
	public ObjectType killerType;
	
	
	public void initialize (GameObject object, GameObject killer){
		this.type = MessageType.destroyObject;
		this.object = object;
		this.objectType = object.getObjectType ();
		
		this.killerType = killer.getObjectType ();
	}
}
