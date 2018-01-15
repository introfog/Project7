package com.introfog.mesh.objects.singletons.character;

import com.badlogic.gdx.utils.Pools;
import com.introfog.mesh.objects.singletons.special.ObjectManager;
import com.introfog.messages.*;

public class CharacterMessageParser extends Character{
	private boolean pushX;
	private boolean pushY;
	private Character character;
	
	
	public CharacterMessageParser (Character character){
		this.character = character;
	}
	
	public boolean parseMessage (GameMessage message){
		if (message.type == MessageType.pushOut){
			PushOutMessage msg = (PushOutMessage) message;
			if (character.body.intersects (msg.undo.oldBodyX - msg.undo.deltaX, msg.undo.oldBodyY - msg.undo.deltaY,
										   msg.undo.bodyW, msg.undo.bodyH)){
				if (msg.undo.object.getNatureType () != character.natureType){
					PlayerLostMessage plm = Pools.obtain (PlayerLostMessage.class);
					plm.initialize (character);
					ObjectManager.getInstance ().addMessage (plm);
				}
				if ((msg.undo.deltaX != 0 && !pushX) || (msg.undo.deltaY != 0 && !pushY)){
					character.body.move (-msg.undo.deltaX, -msg.undo.deltaY);
					pushX = (msg.undo.deltaX != 0);
					pushY = (msg.undo.deltaY != 0);
				}
			}
		}
		else if (message.type == MessageType.move && message.object != character){
			MoveMessage msg = (MoveMessage) message;
			if (character.body.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
				MoveMessage mm = Pools.obtain (MoveMessage.class);
				mm.initialize (character, msg.deltaX, msg.deltaY, character.body.getBodyX (),
							   character.body.getBodyY (), BODY_CHARACTER_W, BODY_CHARACTER_H);
				ObjectManager.getInstance ().addMessage (mm);
				character.body.move (msg.deltaX, msg.deltaY);
			}
		}
		return false;
	}
	
	@Override
	public void update (){
		pushX = false;
		pushY = false;
	}
}