package com.introfog.mesh.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Pools;

import com.introfog.addition.math.Rectangle;
import com.introfog.addition.parsers.ParseBasis;
import com.introfog.mesh.objects.singletons.character.NatureType;
import com.introfog.mesh.objects.singletons.special.ObjectManager;
import com.introfog.messages.*;
import com.introfog.render.*;
import com.introfog.screens.ShowError;

public class Wall implements GameObject{
	private static final float BODY_WALL_W = UNIT;
	private static final float BODY_WALL_H = UNIT * ANGLE;
	
	private NatureType natureType;
	private Rectangle body;
	private DataRender dataRender;
	
	
	public Wall (){
		natureType = NatureType.summer;
		Texture texture = new Texture (ParseBasis.ABSOLUTE_PATH_TO_PROJECT + "resource/images/other/wall.png");
		dataRender = new DataRender ();
		dataRender.sprite = new Sprite (texture);
		dataRender.sprite.setBounds (0, 0, texture.getWidth () * ASPECT_RATIO, texture.getHeight () * ASPECT_RATIO);
		body = new Rectangle (0, 0, BODY_WALL_W, BODY_WALL_H);
	}
	
	public void setSpritePosition (float x, float y, NatureType natureType){
		this.natureType = natureType;
		dataRender.sprite.setPosition (x, y);
		body.setPosition (x + (dataRender.sprite.getWidth () - body.getW ()) / 2, y);
	}
	
	@Override
	public boolean sendMessage (GameMessage message){
		if (message.type == MessageType.move){
			MoveMessage msg = (MoveMessage) message;
			if (body.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
				PushOutMessage pom = Pools.obtain (PushOutMessage.class);
				pom.initialize (msg);
				ObjectManager.getInstance ().addMessage (pom);
				
				if (msg.objectType == ObjectType.character && msg.object.getNatureType () != natureType){
					DestroyObjectMessage dom = Pools.obtain (DestroyObjectMessage.class);
					dom.initialize (msg.object, this);
					ObjectManager.getInstance ().addMessage (dom);
				}
				return true;
			}
		}
		return false;
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