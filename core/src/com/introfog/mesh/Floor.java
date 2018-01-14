package com.introfog.mesh;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

import com.introfog.addition.parsers.ParseBasis;
import com.introfog.mesh.objects.GameObject;
import com.introfog.mesh.objects.singletons.special.LevelManager;

import javafx.util.Pair;

import java.util.ArrayList;

public class Floor{
	private int numRegions;
	private ArrayList <Pair <Pair <Integer, Integer>, Integer>> coordinates;
	private ArrayList <Sprite> floor;
	
	
	public Floor (){
		Texture texture = new Texture (ParseBasis.ABSOLUTE_PATH_TO_PROJECT + "resource/images/other/floor.png");
		numRegions = (int) (texture.getWidth () / (GameObject.UNIT / GameObject.ASPECT_RATIO));
		int w = texture.getWidth () / numRegions;
		int h = texture.getHeight ();
		TextureRegion[] regions = new TextureRegion[numRegions];
		
		for (int i = 0; i < numRegions; i++){
			regions[i] = new TextureRegion (texture, i * w, 0, w, h);
		}
		
		floor = new ArrayList <> (numRegions);
		for (int i = 0; i < numRegions; i++){
			Sprite sprite = new Sprite (regions[i]);
			sprite.setSize (w * GameObject.ASPECT_RATIO + 1, h * GameObject.ASPECT_RATIO + 1);
			floor.add (sprite);
		}
		
		coordinates = new ArrayList <> ();
		int sprite;
		for (int i = 0; i < LevelManager.NUM_TILE_W; i++){
			for (int j = 0; j < LevelManager.NUM_TILE_H; j++){
				sprite = MathUtils.random (0, numRegions - 1);
				coordinates.add (new Pair <> (new Pair <>(i, j), sprite));
			}
		}
	}
	
	public void updateSize (){
		coordinates = new ArrayList <> ();
		int sprite;
		for (int i = 0; i < LevelManager.NUM_TILE_W; i++){
			for (int j = 0; j < LevelManager.NUM_TILE_H; j++){
				sprite = MathUtils.random (0, numRegions - 1);
				coordinates.add (new Pair <> (new Pair <>(i, j), sprite));
			}
		}
	}
	
	public void draw (SpriteBatch batch){
		int x;
		int y;
		for (Pair <Pair <Integer, Integer>, Integer> tmpP : coordinates){
			x = (int) (tmpP.getKey ().getKey () * GameObject.UNIT);
			y = (int) (tmpP.getKey ().getValue () * GameObject.UNIT * GameObject.ANGLE);
			floor.get (tmpP.getValue ()).setPosition (x, y);
			floor.get (tmpP.getValue ()).draw (batch);
		}
	}
}