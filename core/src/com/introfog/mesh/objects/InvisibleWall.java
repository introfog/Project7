package com.introfog.mesh.objects;

import com.introfog.mesh.body.NoSpriteObject;
import com.introfog.mesh.objects.singletons.special.ObjectManager;
import com.introfog.messages.GameMessage;
import com.introfog.messages.MessageType;
import com.introfog.messages.MoveMessage;
import com.introfog.messages.PushOutMessage;

public class InvisibleWall extends GameObject{
	public InvisibleWall (){
		objectType = ObjectType.invisibleWall;
		body = new NoSpriteObject (0, 0, 1, 1);
	}
	
	public void setBodyBounds (float x, float y, float w, float h){
		body.setBodyBounds (x, y, w, h);
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.move && message.objectType == ObjectType.character){
			MoveMessage msg = (MoveMessage) message;
			if (body.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY, msg.bodyW, msg.bodyH)){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (msg.object, -msg.deltaX, 0));
			}
			if (body.intersects (msg.oldBodyX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (msg.object, 0, -msg.deltaY));
			}
		}
	}
}
