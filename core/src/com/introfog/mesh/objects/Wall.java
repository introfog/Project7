package com.introfog.mesh.objects;

import com.badlogic.gdx.utils.Pools;

import com.introfog.mesh.body.BodyObject;
import com.introfog.mesh.objects.singletons.special.ObjectManager;
import com.introfog.messages.GameMessage;
import com.introfog.messages.MessageType;
import com.introfog.messages.MoveMessage;
import com.introfog.messages.PushOutMessage;
import com.introfog.render.DataRender;
import com.introfog.render.LayerType;
import com.introfog.render.Render;

public class Wall extends GameObject{
	private static final float BODY_WALL_W = UNIT;
	private static final float BODY_WALL_H = UNIT * ANGLE;
	private static final float WALL_W = UNIT;
	private static final float WALL_H = UNIT * 2 + UNIT * ANGLE;
	
	
	public Wall (){
		objectType = ObjectType.wall;
		body = new BodyObject ("core/assets/images/other/wall_2.png", 0, 0, WALL_W, WALL_H, BODY_WALL_W, BODY_WALL_H);
		dataRender = new DataRender (body.getSprite (), LayerType.normal);
	}
	
	public void setSpritePosition (float x, float y){
		body.setSpritePosition (x, y);
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.move && (message.objectType == ObjectType.character ||
				message.objectType == ObjectType.box)){
			MoveMessage msg = (MoveMessage) message;
			if (msg.deltaX != 0 &&  body.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY, msg.bodyW, msg.bodyH)){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (msg.object, -msg.deltaX, 0));
			}
			if (msg.deltaY != 0 &&  body.intersects (msg.oldBodyX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (msg.object, 0, -msg.deltaY));
			}
			
		}
	}
	
	@Override
	public void draw (){
		Render.getInstance ().addDataForRender (dataRender);
	}
	
	@Override
	public void clear (){
		Pools.free (this);
	}
}