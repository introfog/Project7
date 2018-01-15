package com.introfog.messages;

import com.introfog.mesh.objects.GameObject;

public class PlayerLostMessage extends GameMessage{
	public void initialize (GameObject object){
		this.type = MessageType.playerLost;
		this.object = object;
		this.objectType = object.getObjectType ();
	}
}
