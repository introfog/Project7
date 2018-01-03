package com.introfog.mesh.objects;

import com.introfog.mesh.body.NoSpriteObject;
import com.introfog.mesh.objects.character.Character;
import com.introfog.mesh.objects.singletons.special.ObjectManager;
import com.introfog.messages.CompleteLevelMessage;
import com.introfog.messages.GameMessage;
import com.introfog.messages.MessageType;
import com.introfog.messages.MoveMessage;

public class FinishLevel extends GameObject{
	private static boolean[] onFinish;
	private static FinishLevel[] detected;
	
	
	public FinishLevel (){
		objectType = ObjectType.finishLevel;
		body = new NoSpriteObject (0, 0, 1, 1);
		
		onFinish = new boolean[2];
		detected = new FinishLevel[2];
	}
	
	public void setBodyBounds (float x, float y, float w, float h){
		body.setBodyBounds (x, y, w, h);
	}
	
	@Override
	public void update (){
		if (onFinish[0] && onFinish[1]){
			ObjectManager.getInstance ().addMessage (new CompleteLevelMessage ());
		}
	}
	
	@Override
	public void sendMessage (GameMessage message){
		if (message.type == MessageType.move && message.objectType == ObjectType.character){
			MoveMessage msg = (MoveMessage) message;
			Character character = (Character) message.object;
			int index = character.getName ().ordinal ();
			
			if (body.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
				onFinish[index] = true;
				detected[index] = this;
			}
			else{
				if (detected[index] == this || detected[index] == null){
					onFinish[index] = false;
					detected[index] = null;
				}
			}
		}
	}
	
	@Override
	public void clear (){
		onFinish[0] = false;
		onFinish[1] = false;
		detected[0] = null;
		detected[1] = null;
	}
}