package com.introfog.messages;

import com.introfog.mesh.objects.GameObject;
import com.introfog.mesh.objects.ObjectType;

public abstract class GameMessage{
	public MessageType type;
	public GameObject object;
	public ObjectType objectType;
}
