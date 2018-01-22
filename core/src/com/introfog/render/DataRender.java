package com.introfog.render;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class DataRender{
	public static final float ALPHA_MODULATION = 0.6f;
	
	public boolean useAlphaModulation = false;
	public LayerType layerType;
	public Sprite sprite;
	
	public DataRender (){
		sprite = new Sprite ();
		layerType = LayerType.normal;
	}
}
