package com.introfog.addition.math;

public class Rectangle{
	private float x;
	private float y;
	private float w;
	private float h;
	
	
	public Rectangle (float x, float y, float w, float h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public void setBounds (float x, float y, float w, float h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public void setPosition (float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public void move (float deltaX, float deltaY){
		x += deltaX;
		y += deltaY;
	}
	
	public float getX (){
		return x;
	}
	
	public float getY (){
		return y;
	}
	
	public float getW (){
		return w;
	}
	
	public float getH (){
		return h;
	}
	
	public boolean intersects (float xx, float yy, float ww, float hh){
		//т.к. мы работаем с прямоугольниками, для пересечения необходимо и достаточно что бы хотя бы
		//одна вершина одного из прямоугольников лежала в другом прямоугольнике
		if ((xx <= x + w) && (yy <= y + h) && (xx >= x) && (yy >= y)){
			return true;
		}
		else if ((xx + ww <= x + w) && (yy <= y + h) && (xx + ww >= x) && (yy >= y)){
			return true;
		}
		else if ((xx <= x + w) && (yy + hh <= y + h) && (xx >= x) && (yy + hh >= y)){
			return true;
		}
		else if ((xx + ww <= x + w) && (yy + hh <= y + h) && (xx + ww >= x) && (yy + hh >= y)){
			return true;
		}
		
		else if ((x <= xx + ww) && (y <= yy + hh) && (x >= xx) && (y >= yy)){
			return true;
		}
		else if ((x + w <= xx + ww) && (y <= yy + hh) && (x + w >= xx) && (y >= yy)){
			return true;
		}
		else if ((x <= xx + ww) && (y + h <= yy + hh) && (x >= xx) && (y + h >= yy)){
			return true;
		}
		else if ((x + w <= xx + ww) && (y + h <= yy + hh) && (x + w >= xx) && (y + h >= yy)){
			return true;
		}
		
		return false;
	}
}