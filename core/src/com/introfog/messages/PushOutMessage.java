package com.introfog.messages;

public class PushOutMessage extends GameMessage{
	public MoveMessage undo;
	
	
	public void initialize (MoveMessage undo){
		this.type = MessageType.pushOut;
		this.object = undo.object;
		this.objectType = object.getObjectType ();
		
		this.undo = undo;
	}
}
