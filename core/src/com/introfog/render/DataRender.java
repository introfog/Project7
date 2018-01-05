package com.introfog.render;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class DataRender{
	public LayerType layerType;
	public Sprite sprite;
	
	public DataRender (Sprite sprite, LayerType layerType){
		this.sprite = sprite;
		this.layerType = layerType;
	}
	
	public DataRender (){
		sprite = new Sprite ();
		layerType = LayerType.normal;
	}
	
	public DataRender (LayerType layerType){
		sprite = new Sprite ();
		this.layerType = layerType;
	}
}
