package com.introfog.addition.parsers;

import com.badlogic.gdx.utils.Pools;

import com.introfog.mesh.objects.*;
import com.introfog.mesh.objects.box.Box;
import com.introfog.mesh.objects.singletons.character.Character;
import com.introfog.mesh.objects.singletons.character.NatureType;
import com.introfog.mesh.objects.singletons.special.*;
import com.introfog.messages.AddObjectMessage;
import com.introfog.screens.ShowError;

import javax.xml.stream.*;

import static com.introfog.mesh.objects.GameObject.ASPECT_RATIO;

public abstract class ParseLevel extends ParseBasis{
	private static float x;
	private static float y;
	private static float w;
	private static float h;
	private static int levelH; //высота уровня умноженная на аспект ратио
	private static NatureType type;
	
	
	private static void additionalCalculates (XMLStreamReader xmlReader) throws XMLStreamException, NumberFormatException{
		//int tileW;
		int tileH = 0;
		
		if (xmlReader != null && xmlReader.hasNext ()){
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
		
		levelH = (int) (LevelManager.NUM_TILE_H * tileH * ASPECT_RATIO);
	}
	
	private static void parseCoordinates (XMLStreamReader xmlReader){
		if (xmlReader.getLocalName ().compareTo ("object") == 0){
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
		}
	}
	
	private static void createObject (String currObjectGroup){
		AddObjectMessage aom;
		switch (currObjectGroup){
		case "wall":
			Wall wall = Pools.obtain (Wall.class);
			wall.setSpritePosition (x, y, type);
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
			box.setSpritePosition (x, y, type);
			aom = Pools.obtain (AddObjectMessage.class);
			aom.initialize (box);
			ObjectManager.getInstance ().sendMessage (aom);
			break;
		}
	}
	
	
	public static void parseLVL (int level){
		XMLStreamReader xmlReader = getXML ("resource/xml/levels/lvl" + String.valueOf (level) + ".tmx");
		
		try{
			additionalCalculates (xmlReader);
			
			boolean setType = false;
			String currObjectGroup = "";
			
			while (xmlReader != null && xmlReader.hasNext ()){
				if (!setType){
					type = NatureType.summer;
				}
				xmlReader.next ();
				if (xmlReader.isStartElement ()){
					for (int i = 0; i < xmlReader.getAttributeCount (); i++){
						if (xmlReader.getAttributeName (i).toString ().compareTo ("name") == 0){
							if (xmlReader.getAttributeValue (i).compareTo ("type") != 0){
								currObjectGroup = xmlReader.getAttributeValue (i);
							}
						}
					}
					parseCoordinates (xmlReader);
					if (xmlReader.getLocalName ().compareTo ("property") == 0){
						for (int i = 0; i < xmlReader.getAttributeCount (); i++){
							if (xmlReader.getAttributeName (i).toString ().compareTo ("value") == 0){
								if (xmlReader.getAttributeValue (i).compareTo ("winter") == 0){
									type = NatureType.winter;
									setType = true;
								}
								else{
									type = NatureType.summer;
									setType = true;
								}
							}
						}
					}
				}
				if (xmlReader.isEndElement () && xmlReader.getLocalName ().compareTo ("object") == 0){
					createObject (currObjectGroup);
					setType = false;
				}
			}
		}
		catch (XMLStreamException ex){
			ShowError.getInstance ().initialize ("проблема с созданием уровня под номером - " + level,
												 "был сгенерирован XMLStreamException.");
		}
		catch (NumberFormatException ex){
			ShowError.getInstance ().initialize ("проблема с созданием уровня под номером - " + level,
												 "неверный формат данных.");
		}
	}
}