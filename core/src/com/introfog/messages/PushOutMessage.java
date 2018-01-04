package com.introfog.messages;

public class PushOutMessage extends GameMessage{
	public MoveMessage undo;
	
	
	public PushOutMessage (MoveMessage undo){
		this.type = MessageType.pushOut;
		this.object = undo.object;
		this.objectType = object.objectType;
		
		this.undo = undo;
	}
}
