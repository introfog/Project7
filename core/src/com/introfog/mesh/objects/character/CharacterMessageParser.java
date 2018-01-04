package com.introfog.mesh.objects.character;

import com.introfog.messages.*;

public class CharacterMessageParser extends Character{
	private Character character;
	
	
	public CharacterMessageParser (Character character){
		this.character = character;
	}
	
	public boolean parseMessage (GameMessage message){
		if (message.type == MessageType.pushOut && message.object == character){
			PushOutMessage msg = (PushOutMessage) message;
			character.move (-msg.undo.deltaX, -msg.undo.deltaY);
			return true;
		}
		return false;
	}
}