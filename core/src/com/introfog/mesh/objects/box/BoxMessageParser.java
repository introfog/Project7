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
		if (message.type == MessageType.move && message.object != box){
			MoveMessage msg = (MoveMessage) message;
			if (box.body.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
				MoveMessage mm = Pools.obtain (MoveMessage.class);
				mm.initialize (box, msg.deltaX, msg.deltaY, box.body.getBodyX (), box.body.getBodyY (),
							   BODY_BOX_W, BODY_BOX_H);
				ObjectManager.getInstance ().addMessage (mm);
				box.body.move (msg.deltaX, msg.deltaY);
				
				if (msg.objectType == ObjectType.character && msg.object.getNatureType () != box.natureType){
					DestroyObjectMessage dom = Pools.obtain (DestroyObjectMessage.class);
					dom.initialize (msg.object, box);
					ObjectManager.getInstance ().addMessage (dom);
					return true;
				}
			}
		}
		else if (message.type == MessageType.pushOut){
			PushOutMessage pushOutMessage = (PushOutMessage) message;
			MoveMessage msg = pushOutMessage.undo;
			if (box.body.intersects (msg.oldBodyX, msg.oldBodyY, msg.bodyW, msg.bodyH)){
				MoveMessage mm = Pools.obtain (MoveMessage.class);
				mm.initialize (box, -msg.deltaX, -msg.deltaY, box.body.getBodyX (), box.body.getBodyY (),
							   BODY_BOX_W, BODY_BOX_H);
				ObjectManager.getInstance ().addMessage (mm);
				box.body.move (-msg.deltaX, -msg.deltaY);
			}
		}
		return false;
	}
}