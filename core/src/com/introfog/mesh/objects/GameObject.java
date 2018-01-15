package com.introfog.mesh.objects;

import com.introfog.mesh.objects.singletons.character.NatureType;
import com.introfog.messages.GameMessage;

import static com.introfog.GameSystem.SCREEN_H;
import static com.introfog.GameSystem.SCREEN_W;

public interface GameObject{
	float ASPECT_RATIO = (float) ((SCREEN_W / 2 < SCREEN_H) ? SCREEN_W / 1366 : SCREEN_H / 768);
	float UNIT = 64 * ASPECT_RATIO; //условный метр в игре
	float ANGLE = 0.75f;
	
	
	default void update (){ }
	boolean sendMessage (GameMessage message);
	default void draw (){ }
	default void clear (){ }
	default ObjectType getObjectType (){
		return ObjectType.unknown;
	}
	default NatureType getNatureType (){
		return NatureType.summer;
	}
}