package com.introfog.mesh.objects.singletons.character;

import com.introfog.addition.parsers.ParseBasis;
import com.introfog.mesh.animation.ObjectAnimation;
import com.introfog.mesh.objects.GameObject;
import com.introfog.render.*;

public class CharacterAnimations extends Character{
	private Character character;
	private DataRender dataRender;
	
	private ObjectAnimation[] stand;
	private ObjectAnimation[] walk;
	
	
	public CharacterAnimations (Character character, NatureType natureType){
		this.natureType = natureType;
		this.character = character;
		String path = ParseBasis.ABSOLUTE_PATH_TO_PROJECT + "resource/images/character/" + natureType.toString () + "/";
		
		stand = new ObjectAnimation[Direction.values ().length];
		walk = new ObjectAnimation[Direction.values ().length];
		
		float region = GameObject.UNIT / GameObject.ASPECT_RATIO;
		for (int i = 0; i < Direction.values ().length; i++){
			stand[i] = new ObjectAnimation (path + "stand/stand_" + Direction.values ()[i] + ".png", region,
					region, CHARACTER_W, CHARACTER_H, 0.2f);
			walk[i] = new ObjectAnimation (path + "walk/walk_" + Direction.values ()[i] + ".png", region,
					region, CHARACTER_W, CHARACTER_H, 0.2f);
		}
		
		dataRender = new DataRender ();
	}
	
	@Override
	public void update (){
		switch (character.state){
		case stand:
			dataRender.sprite = stand[character.currentDirection.ordinal ()].getCurrSprite ();
			break;
		case move:
			dataRender.sprite = walk[character.currentDirection.ordinal ()].getCurrSprite ();
			break;
		}
		dataRender.sprite.setPosition (character.body.getSpriteX (), character.body.getSpriteY ());
	}
	
	@Override
	public void draw (){
		Render.getInstance ().addDataForRender (dataRender);
	}
	
	@Override
	public void clear (){
		for (int i = 0; i < Direction.values ().length; i++){
			stand[i].resetTime ();
			walk[i].resetTime ();
		}
	}
}