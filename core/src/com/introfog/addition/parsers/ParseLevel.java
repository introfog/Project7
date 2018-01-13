package com.introfog.addition.parsers;

import com.badlogic.gdx.utils.Pools;

import com.introfog.mesh.objects.*;
import com.introfog.mesh.objects.box.Box;
import com.introfog.mesh.objects.singletons.character.Character;
import com.introfog.mesh.objects.singletons.special.*;
import com.introfog.messages.AddObjectMessage;

import javax.xml.stream.*;

import static com.introfog.mesh.objects.GameObject.ASPECT_RATIO;

public abstract class ParseLevel extends ParseBasis{
	private static float x;
	private static float y;
	private static float w;
	private static float h;
	private static int levelH; //высота уровня умноженная на аспект ратио
	
	
	private static void additionalCalculates (XMLStreamReader xmlReader){
		//int tileW;
		int tileH = 0;
		try{
			if (xmlReader.hasNext ()){
				xmlReader.next ();
				if (xmlReader.isStartElement () && xmlReader.getLocalName ().compareTo ("map") == 0){
					for (int i = 0; i < xmlReader.getAttributeCount (); i++){
						switch (xmlReader.getAttributeName (i).toString ()){
						case "width":
							LevelManager.NUM_TILE_W = Integer.parseInt (xmlReader.getAttributeValue (i));
							break;
						case "height":
							LevelManager.NUM_TILE_H = Integer.parseInt (xmlReader.getAttributeValue (i));
							break;
						/*case "tilewidth":
							tileW = Integer.parseInt (xmlReader.getAttributeValue (i));
							break;*/
						case "tileheight":
							tileH = Integer.parseInt (xmlReader.getAttributeValue (i));
							break;
						}
					}
				}
			}
		}
		catch (XMLStreamException | NumberFormatException | NullPointerException ex){
			ex.printStackTrace (System.out);
		}
		
		levelH = (int) (LevelManager.NUM_TILE_H * tileH * ASPECT_RATIO);
	}
	
	private static void createObject (String currObjectGroup){
		AddObjectMessage aom;
		switch (currObjectGroup){
		case "wall":
			Wall wall = Pools.obtain (Wall.class);
			wall.setSpritePosition (x, y);
			aom = Pools.obtain (AddObjectMessage.class);
			aom.initialize (wall);
			ObjectManager.getInstance ().sendMessage (aom);
			break;
		case "character":
			Character character;
			character = Character.getInstance ();
			character.setSpritePosition (x, y);
			aom = Pools.obtain (AddObjectMessage.class);
			aom.initialize (character);
			ObjectManager.getInstance ().sendMessage (aom);
			break;
		case "invisibleWall":
			InvisibleWall invisibleWall = Pools.obtain (InvisibleWall.class);
			invisibleWall.setBodyBounds (x, y, w, h);
			aom = Pools.obtain (AddObjectMessage.class);
			aom.initialize (invisibleWall);
			ObjectManager.getInstance ().sendMessage (aom);
			break;
		case "finishLevel":
			FinishLevel finish = Pools.obtain (FinishLevel.class);
			finish.setBodyBounds (x, y, w, h);
			aom = Pools.obtain (AddObjectMessage.class);
			aom.initialize (finish);
			ObjectManager.getInstance ().sendMessage (aom);
			break;
		case "box":
			Box box = Pools.obtain (Box.class);
			box.setSpritePosition (x, y);
			aom = Pools.obtain (AddObjectMessage.class);
			aom.initialize (box);
			ObjectManager.getInstance ().sendMessage (aom);
			break;
		}
	}
	
	
	public static void parseLVL (int level){
		XMLStreamReader xmlReader = getXML ("core/assets/xml/levels/lvl" + String.valueOf (level) + ".tmx",
											"/resource/xml/levels/lvl" + String.valueOf (level) + ".tmx");
		
		additionalCalculates (xmlReader);
		
		String currObjectGroup = "";
		try{
			while (xmlReader != null && xmlReader.hasNext ()){
				xmlReader.next ();
				if (xmlReader.isStartElement ()){
					if (xmlReader.getAttributeName (0).toString ().compareTo ("name") == 0){
						currObjectGroup = xmlReader.getAttributeValue (0);
					}
					else if (xmlReader.getLocalName ().compareTo ("object") == 0){
						for (int i = 0; i < xmlReader.getAttributeCount (); i++){
							switch (xmlReader.getAttributeName (i).toString ()){
							case "x":
								x = Float.parseFloat (xmlReader.getAttributeValue (i));
								x *= ASPECT_RATIO;
								break;
							case "y":
								y = Float.parseFloat (xmlReader.getAttributeValue (i));
								
								break;
							case "width":
								w = Float.parseFloat (xmlReader.getAttributeValue (i));
								w *= ASPECT_RATIO;
								break;
							case "height":
								h = Float.parseFloat (xmlReader.getAttributeValue (i));
								h *= ASPECT_RATIO;
								break;
							}
						}
						y = levelH - y * ASPECT_RATIO - h;
						createObject (currObjectGroup);
					}
				}
			}
		}
		catch (XMLStreamException ex){
			ex.printStackTrace (System.out);
		}
	}
}