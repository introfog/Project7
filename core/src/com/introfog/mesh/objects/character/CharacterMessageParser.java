package com.introfog.mesh.objects.character;

import com.introfog.mesh.objects.singletons.special.ObjectManager;
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
		else if (message.type == MessageType.pushOut){
			PushOutMessage msg = (PushOutMessage) message;
			if (character.intersects (msg.undo.oldBodyX - msg.undo.deltaX, msg.undo.oldBodyY - msg.undo.deltaY,
									  msg.undo.bodyW, msg.undo.bodyH)){
				ObjectManager.getInstance ().addMessage (
						new MoveMessage (character, -msg.undo.deltaX, -msg.undo.deltaY, character.getBodyX (),
										 character.getBodyY (), character.getSpriteX (), character.getSpriteY (),
										 character.getBodyW (), character.getBodyH ()));
				character.move (-msg.undo.deltaX, -msg.undo.deltaY);
			}
		}
		return false;
	}
}