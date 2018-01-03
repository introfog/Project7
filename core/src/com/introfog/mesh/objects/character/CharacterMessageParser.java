package com.introfog.mesh.objects.character;

import com.introfog.mesh.objects.ObjectType;
import com.introfog.mesh.objects.singletons.special.ObjectManager;
import com.introfog.messages.*;

public class CharacterMessageParser extends Character{
	private boolean pushOutHorizontal = false;
	private boolean pushOutVertical = false;
	private Character character;
	
	
	private void pushOutMessage (GameMessage message){
		PushOutMessage msg = (PushOutMessage) message;
		if (msg.deltaX != 0 && !pushOutHorizontal){
			character.move (msg.deltaX, 0);
			ObjectManager.getInstance ().addMessage (new MoveMessage (character, msg.deltaX, 0, character.getBodyX (),
					character.getBodyY (), character.getSpriteX (), character.getSpriteY (), character.getBodyW (), character.getBodyH ()));
			pushOutHorizontal = true;
		}
		if (msg.deltaY != 0 && !pushOutVertical){
			character.move (0, msg.deltaY);
			ObjectManager.getInstance ().addMessage (new MoveMessage (character, 0, msg.deltaY, character.getBodyX (),
					character.getBodyY (), character.getSpriteX (), character.getSpriteY (), character.getBodyW (), character.getBodyH ()));
			pushOutVertical = true;
		}
	}
	
	
	public CharacterMessageParser (Character character){
		this.character = character;
	}
	
	@Override
	public void update (){
		pushOutHorizontal = false;
		pushOutVertical = false;
	}
	
	public void parseMessage (GameMessage message){
		if (message.type == MessageType.move && message.objectType == ObjectType.character){
			if (message.object != character){
				MoveMessage msg = (MoveMessage) message;
				if (character.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
					ObjectManager.getInstance ().addMessage (new PushOutMessage (msg.object, -msg.deltaX, -msg.deltaY));
				}
			}
		}
		else if (message.type == MessageType.pushOut && message.object == character){
			pushOutMessage (message);
		}
	}
}