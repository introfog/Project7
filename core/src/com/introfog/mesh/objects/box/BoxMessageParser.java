package com.introfog.mesh.objects.box;

import com.badlogic.gdx.utils.Pools;
import com.introfog.mesh.objects.ObjectType;
import com.introfog.mesh.objects.singletons.special.ObjectManager;
import com.introfog.messages.*;

public class BoxMessageParser extends Box{
	private Box box;
	
	public BoxMessageParser (Box box){
		this.box = box;
	}
	
	public boolean parseMessage (GameMessage message){
		if (message.type == MessageType.move && message.objectType == ObjectType.character){
			MoveMessage msg = (MoveMessage) message;
			if (box.body.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
				MoveMessage mm = Pools.obtain (MoveMessage.class);
				mm.initialize (box, msg.deltaX, msg.deltaY, box.body.getBodyX (), box.body.getBodyY (),
							   box.body.getSpriteX (), box.body.getSpriteY (), BODY_BOX_W, BODY_BOX_H);
				ObjectManager.getInstance ().addMessage (mm);
				box.body.move (msg.deltaX, msg.deltaY);
				return true;
			}
		}
		else if (message.type == MessageType.move && message.objectType == ObjectType.box && message.object != box){
			MoveMessage msg = (MoveMessage) message;
			if (box.body.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
				PushOutMessage pom = Pools.obtain (PushOutMessage.class);
				pom.initialize (msg);
				ObjectManager.getInstance ().addMessage (pom);
				return true;
			}
		}
		else if (message.type == MessageType.pushOut && message.object == box){
			PushOutMessage msg = (PushOutMessage) message;
			box.body.move (-msg.undo.deltaX, -msg.undo.deltaY);
		}
		return false;
	}
}