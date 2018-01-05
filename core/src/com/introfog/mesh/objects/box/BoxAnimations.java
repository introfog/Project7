package com.introfog.mesh.objects.box;

import com.introfog.mesh.animation.ObjectAnimation;
import com.introfog.mesh.objects.GameObject;
import com.introfog.render.DataRender;
import com.introfog.render.Render;

public class BoxAnimations extends Box{
	private Box box;
	private DataRender dataRender;
	private ObjectAnimation fall;
	
	
	public BoxAnimations (Box box){
		this.box = box;
		
		float regionW = GameObject.UNIT / GameObject.ASPECT_RATIO;
		float regionH = (1 + GameObject.ANGLE) * GameObject.UNIT / GameObject.ASPECT_RATIO;
		fall = new ObjectAnimation ("core/assets/images/other/box.png", false, regionW, regionH, BOX_W, BOX_H, 0.3f);
		
		dataRender = new DataRender ();
		dataRender.sprite = fall.getFirstFrame ();
		dataRender.sprite.setPosition (box.body.getSpriteX (), box.body.getSpriteY ());
	}
	
	@Override
	public void update (){
		dataRender.sprite = fall.getFirstFrame ();
		dataRender.sprite.setPosition (box.body.getSpriteX (), box.body.getSpriteY ());
	}
	
	@Override
	public void draw (){
		Render.getInstance ().addDataForRender (dataRender);
	}
}