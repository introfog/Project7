package com.introfog.mesh.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Pools;

import com.introfog.addition.math.Rectangle;
import com.introfog.mesh.objects.singletons.special.ObjectManager;
import com.introfog.messages.*;
import com.introfog.render.*;

public class Wall extends GameObject{
	private static final float BODY_WALL_W = UNIT;
	private static final float BODY_WALL_H = UNIT * ANGLE;
	
	private Rectangle body;
	private Sprite sprite;
	
	
	public Wall (){
		objectType = ObjectType.wall;
		
		Texture texture = new Texture ("core/assets/images/other/wall.png");
		sprite = new Sprite (texture);
		sprite.setBounds (0, 0, texture.getWidth () * ASPECT_RATIO, texture.getHeight () * ASPECT_RATIO);
		body = new Rectangle (0, 0, BODY_WALL_W, BODY_WALL_H);
		
		dataRender = new DataRender (sprite, LayerType.normal);
	}
	
	public void setSpritePosition (float x, float y){
		sprite.setPosition (x, y);
		body.setPosition (x + (sprite.getWidth () - body.getW ()) / 2, y);
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.move){
			MoveMessage msg = (MoveMessage) message;
			if (msg.deltaX != 0 && body.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY, msg.bodyW, msg.bodyH)){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (msg));
				System.out.println ("Push out x");
			}
			if (msg.deltaY != 0 && body.intersects (msg.oldBodyX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
				ObjectManager.getInstance ().addMessage (new PushOutMessage (msg));
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