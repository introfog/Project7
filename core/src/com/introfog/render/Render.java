package com.introfog.render;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.introfog.mesh.Floor;
import com.introfog.mesh.objects.singletons.Camera;

import java.util.LinkedList;

public class Render{
	private Floor floor;
	private SpriteBatch batch;
	private LinkedList <DataRender> renderList;
	
	
	private void sortedScene (){
		renderList.sort ((tmp1, tmp2) -> {
			if (tmp1.layerType == LayerType.over || tmp2.layerType == LayerType.below){
				return 1;
			}
			else if (tmp2.layerType == LayerType.over || tmp1.layerType == LayerType.below){
				return -1;
			}
			
			return (int) (tmp2.sprite.getY () - tmp1.sprite.getY ());
		});
	}
	
	private static class RenderHolder{
		private final static Render instance = new Render ();
	}
	
	private Render (){
		floor = new Floor ();
		batch = new SpriteBatch ();
		renderList = new LinkedList <> ();
	}
	
	
	public static Render getInstance (){
		return RenderHolder.instance;
	}
	
	public void renderScene (){
		Gdx.gl.glClearColor (0, 0, 0, 1);
		Gdx.gl.glClear (GL20.GL_COLOR_BUFFER_BIT);
		
		sortedScene ();
		
		Camera.getInstance ().update ();
		batch.setProjectionMatrix (Camera.getInstance ().getProjectionMatrix ());
		
		batch.begin ();
		floor.draw (batch);
		for (DataRender data : renderList){
			data.sprite.draw (batch);
		}
		batch.end ();
		
		renderList.clear ();
	}
	
	public void addDataForRender (DataRender data){
		renderList.add (data);
	}
}