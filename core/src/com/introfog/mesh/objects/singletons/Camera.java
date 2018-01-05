package com.introfog.mesh.objects.singletons;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;

import com.introfog.GameSystem;
import com.introfog.mesh.objects.GameObject;
import com.introfog.mesh.objects.ObjectType;
import com.introfog.messages.*;

public class Camera extends GameObject{
	private OrthographicCamera camera;
	
	
	private static class CameraHolder{
		private final static Camera instance = new Camera ();
	}
	
	private Camera (){
		objectType = ObjectType.camera;
		
		camera = new OrthographicCamera (GameSystem.SCREEN_W, GameSystem.SCREEN_H);
		camera.setToOrtho (false);
	}
	
	
	public static Camera getInstance (){
		return CameraHolder.instance;
	}
	
	public Matrix4 getProjectionMatrix (){
		return camera.combined;
	}
	
	public void setPosition (float x, float y){
		camera.position.set (x, y, 0);
	}
	
	@Override
	public void update (){
		camera.update ();
	}
	
	@Override
	public boolean sendMessage (GameMessage message){
		return false;
	}
}