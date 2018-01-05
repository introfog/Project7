package com.introfog.messages;

public class CompleteLevelMessage extends GameMessage{
	public void initialize (){
		this.type = MessageType.levelComplete;
	}
}
