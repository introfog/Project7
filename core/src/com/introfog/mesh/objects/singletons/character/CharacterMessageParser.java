package com.introfog.mesh.objects.singletons.character;

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
				/*if (msg.undo.deltaY != 0){
					System.out.println ("....In class Character....");
					System.out.println ("Character push out, deltaX: " + (-msg.undo.deltaX) + " deltaY: " + (-msg.undo.deltaY) + " Push out object: " + msg.objectType);
					System.out.println ("Undo object: " + msg.undo.objectType);
					System.out.println ("Message was added in ObjectManager in class Character: " + msg.objectType + " " + msg.type + " " + msg.object);
					System.out.println ("PushOutMessage msg: " + msg);
					System.out.println ("PushOutMessage message: " + message);
					System.out.println ("Undo in PushOutMessage: " + msg.undo);
					System.out.println ("....Finish class Character....");
				}*/
			}
		}
		return false;
	}
}