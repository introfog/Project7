package com.introfog.mesh.objects;

import com.introfog.mesh.body.Body;
import com.introfog.messages.GameMessage;
import com.introfog.render.DataRender;

import static com.introfog.GameSystem.SCREEN_H;
import static com.introfog.GameSystem.SCREEN_W;

public abstract class GameObject{
	public static GameObject triggered[] = new GameObject[2];
	
	public static final float ASPECT_RATIO = (float) ((SCREEN_W / 2 < SCREEN_H) ? SCREEN_W / 1366 : SCREEN_H / 768);
	public static final float UNIT = 64 * ASPECT_RATIO; //условный метр в игре
	public static final float ANGLE = 0.75f;
	
	public ObjectType objectType = ObjectType.unknown;
	
	protected DataRender dataRender;
	protected Body body;
	
	
	public void update (){ }
	abstract public void sendMessage (GameMessage message); //обязательный метод у объекта
	public void draw (){ }
	public void clear (){ }
}