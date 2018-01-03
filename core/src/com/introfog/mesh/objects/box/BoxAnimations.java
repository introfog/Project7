package com.introfog.mesh.objects.box;

import com.badlogic.gdx.graphics.g2d.Sprite;

import com.introfog.mesh.animation.ObjectAnimation;
import com.introfog.mesh.objects.GameObject;
import com.introfog.render.DataRender;
import com.introfog.render.LayerType;
import com.introfog.render.Render;

public class BoxAnimations extends Box{
	private Box box;
	private Sprite currSprite;
	private ObjectAnimation fall;
	
	
	public BoxAnimations (Box box){
		this.box = box;
		
		float regionW = GameObject.UNIT / GameObject.ASPECT_RATIO;
		float regionH = (1 + GameObject.ANGLE) * GameObject.UNIT / GameObject.ASPECT_RATIO;
		fall = new ObjectAnimation ("core/assets/images/other/box.png", false, regionW, regionH, BOX_W, BOX_H, 0.3f);
		currSprite = fall.getFirstFrame ();
		currSprite.setPosition (box.getSpriteX (), box.getSpriteY ());
		
		dataRender = new DataRender (currSprite, LayerType.normal);
	}
	
	@Override
	public void update (){
		currSprite = fall.getFirstFrame ();
		currSprite.setPosition (box.getSpriteX (), box.getSpriteY ());
	}
	
	@Override
	public void draw (){
		dataRender.sprite = currSprite;
		Render.getInstance ().addDataForRender (dataRender);
	}
}