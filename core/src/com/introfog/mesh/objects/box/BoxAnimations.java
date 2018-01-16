package com.introfog.mesh.objects.box;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.introfog.addition.parsers.ParseBasis;
import com.introfog.mesh.objects.singletons.character.NatureType;
import com.introfog.render.DataRender;
import com.introfog.render.Render;
import com.introfog.screens.ShowError;

public class BoxAnimations extends Box{
	private Box box;
	private DataRender dataRender;
	private Sprite spriteSummer;
	private Sprite spriteWinter;
	
	
	public BoxAnimations (Box box){
		this.box = box;
		try{
			Texture texture;
			texture = new Texture (ParseBasis.ABSOLUTE_PATH_TO_PROJECT + "resource/images/other/box_summer.png");
			spriteSummer = new Sprite (texture);
			spriteSummer.setSize (BOX_W, BOX_H);
			
			texture = new Texture (ParseBasis.ABSOLUTE_PATH_TO_PROJECT + "resource/images/other/box_winter.png");
			spriteWinter = new Sprite (texture);
			spriteWinter.setSize (BOX_W, BOX_H);
			
			dataRender = new DataRender ();
			dataRender.sprite = spriteSummer;
			dataRender.sprite.setPosition (box.body.getSpriteX (), box.body.getSpriteY ());
		}
		catch (RuntimeException ex){
			ShowError.getInstance ().initialize ("проблема с созданием объекта Box",
												 "дело в resource/images/other/box_summer иди winter.png");
		}
	}
	
	@Override
	public void update (){
		if (box.natureType == NatureType.summer){
			dataRender.sprite = spriteSummer;
		}
		else if (box.natureType == NatureType.winter){
			dataRender.sprite = spriteWinter;
		}
		dataRender.sprite.setPosition (box.body.getSpriteX (), box.body.getSpriteY ());
	}
	
	@Override
	public void draw (){
		Render.getInstance ().addDataForRender (dataRender);
	}
}