package com.introfog.mesh.objects;

import com.badlogic.gdx.utils.Pools;
import com.introfog.addition.math.Rectangle;
import com.introfog.mesh.objects.singletons.special.ObjectManager;
import com.introfog.messages.*;

public class InvisibleWall extends GameObject{
	private Rectangle body;
	
	
	public InvisibleWall (){
		objectType = ObjectType.invisibleWall;
		body = new Rectangle (0, 0, 1, 1);
	}
	
	public void setBodyBounds (float x, float y, float w, float h){
		body.setBounds (x, y, w, h);
	}
	
	@Override
	public boolean sendMessage (GameMessage message){
		if (message.type == MessageType.move && message.objectType == ObjectType.character){
			MoveMessage msg = (MoveMessage) message;
			if (msg.deltaX != 0 && body.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY, msg.bodyW, msg.bodyH)){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (msg));
				return true;
			}
			if (msg.deltaY != 0 && body.intersects (msg.oldBodyX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (msg));
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void clear (){
		Pools.free (this);
	}
}