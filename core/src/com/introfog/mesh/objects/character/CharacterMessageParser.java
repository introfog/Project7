package com.introfog.mesh.objects.character;

import com.introfog.messages.*;

public class CharacterMessageParser extends Character{
	private Character character;
	
	
	public CharacterMessageParser (Character character){
		this.character = character;
	}
	
	public boolean parseMessage (GameMessage message){
		if (message.type == MessageType.pushOut){
			PushOutMessage msg = (PushOutMessage) message;
			if (character.body.intersects (msg.undo.oldBodyX - msg.undo.deltaX, msg.undo.oldBodyY - msg.undo.deltaY,
									  msg.undo.bodyW, msg.undo.bodyH)){
				character.body.move (-msg.undo.deltaX, -msg.undo.deltaY);
			}
		}
		return false;
	}
}