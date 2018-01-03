package com.introfog.mesh.objects.character;

import com.badlogic.gdx.graphics.g2d.Sprite;

import com.introfog.mesh.animation.ObjectAnimation;
import com.introfog.mesh.objects.GameObject;
import com.introfog.render.DataRender;
import com.introfog.render.LayerType;
import com.introfog.render.Render;

public class CharacterAnimations extends Character{
	private Character character;
	private Sprite currSprite;
	
	private ObjectAnimation[] stand;
	private ObjectAnimation[] walk;
	private ObjectAnimation[] fall;
	private ObjectAnimation[] choke;
	private ObjectAnimation[] push;
	private ObjectAnimation[] abut;
	
	
	public CharacterAnimations (Character character){
		this.character = character;
		String path;
		if (character.getName () == CharacterName.first){
			path = "core/assets/images/character/first/";
		}
		else{
			path = "core/assets/images/character/second/";
		}
		
		stand = new ObjectAnimation[Direction.values ().length];
		walk = new ObjectAnimation[Direction.values ().length];
		fall = new ObjectAnimation[Direction.values ().length];
		choke = new ObjectAnimation[Direction.values ().length];
		push = new ObjectAnimation[Direction.values ().length];
		abut = new ObjectAnimation[Direction.values ().length];
		
		float region = GameObject.UNIT / GameObject.ASPECT_RATIO;
		for (int i = 0; i < Direction.values ().length; i++){
			stand[i] = new ObjectAnimation (path + "stand/stand_" + Direction.values ()[i] + ".png", region,
					region, CHARACTER_W, CHARACTER_H, 0.35f);
			walk[i] = new ObjectAnimation (path + "walk/walk_" + Direction.values ()[i] + ".png", region,
					region, CHARACTER_W, CHARACTER_H, 0.15f);
			fall[i] = new ObjectAnimation (path + "fall/fall_" + Direction.values ()[i] + ".png",
					false, region, region, CHARACTER_W, CHARACTER_H, 0.15f);
			choke[i] = new ObjectAnimation (path + "choke/choke_" + Direction.values ()[i] + ".png",
					false, region, region, CHARACTER_W, CHARACTER_H, 0.3f);
			push[i] = new ObjectAnimation (path + "push/push_" + Direction.values ()[i] + ".png", region,
					region, CHARACTER_W, CHARACTER_H, 0.15f);
			abut[i] = new ObjectAnimation (path + "abut/abut_" + Direction.values ()[i] + ".png", region,
					region, CHARACTER_W, CHARACTER_H, 0.15f);
		}
		
		dataRender = new DataRender (currSprite, LayerType.normal);
	}
	
	@Override
	public void update (){
		switch (character.state){
		case stand:
			currSprite = stand[character.currentDirection.ordinal ()].getCurrSprite ();
			break;
		case move:
			currSprite = walk[character.currentDirection.ordinal ()].getCurrSprite ();
			break;
		case fall:
			currSprite = fall[character.currentDirection.ordinal ()].getCurrSprite ();
			break;
		case choke:
			currSprite = choke[character.currentDirection.ordinal ()].getCurrSprite ();
			break;
		case push:
			currSprite = push[character.currentDirection.ordinal ()].getCurrSprite ();
			break;
		case abut:
			currSprite = abut[character.currentDirection.ordinal ()].getCurrSprite ();
			break;
		}
		currSprite.setPosition (character.getSpriteX (), character.getSpriteY ());
	}
	
	@Override
	public void draw (){
		dataRender.sprite = currSprite;
		Render.getInstance ().addDataForRender (dataRender);
	}
	
	@Override
	public void clear (){
		for (int i = 0; i < Direction.values ().length; i++){
			stand[i].resetTime ();
			walk[i].resetTime ();
			fall[i].resetTime ();
			choke[i].resetTime ();
			push[i].resetTime ();
			abut[i].resetTime ();
		}
	}
}