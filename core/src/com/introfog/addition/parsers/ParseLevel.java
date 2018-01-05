package com.introfog.addition.parsers;

import com.badlogic.gdx.utils.Pools;

import com.introfog.GameSystem;
import com.introfog.mesh.objects.*;
import com.introfog.mesh.objects.box.Box;
import com.introfog.mesh.objects.singletons.character.Character;
import com.introfog.mesh.objects.singletons.special.ObjectManager;
import com.introfog.messages.AddObjectMessage;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import static com.introfog.mesh.objects.GameObject.ASPECT_RATIO;

public abstract class ParseLevel extends ParseBasis{
	private static float x;
	private static float y;
	private static float w;
	private static float h;
	private static int levelH; //высота уровня умноженная на аспект ратио
	
	
	private static void additionalCalculates (Node map){
		levelH = Integer.parseInt (map.getAttributes ().item (5).getTextContent ()); //tile height
		int height = Integer.parseInt (map.getAttributes ().item (0).getTextContent ());
		levelH *= height;
		levelH *= ASPECT_RATIO;
		
		//ширина уровня умноженная на аспект ратио
		int levelW = Integer.parseInt (map.getAttributes ().item (6).getTextContent ()); //tile width
		int width = Integer.parseInt (map.getAttributes ().item (8).getTextContent ());
		levelW *= width;
		levelW *= ASPECT_RATIO;
		
		GameSystem.INDENT_BETWEEN_SCREEN_LEVEL = (GameSystem.SCREEN_W - levelW) / 2;
	}
	
	private static void parseCoordinates (Node object){
		w = Float.parseFloat (object.getAttributes ().item (2).getTextContent ());
		w *= ASPECT_RATIO;
		h = Float.parseFloat (object.getAttributes ().item (0).getTextContent ());
		h *= ASPECT_RATIO;
		
		x = Float.parseFloat (object.getAttributes ().item (3).getTextContent ());
		x = x * ASPECT_RATIO + GameSystem.INDENT_BETWEEN_SCREEN_LEVEL;
		y = Float.parseFloat (object.getAttributes ().item (4).getTextContent ());
		y = levelH - y * ASPECT_RATIO - h;
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
		case "characters":
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
		Document document = getDocument ("core/assets/xml/levels/lvl" + String.valueOf (level) + ".tmx",
				"/resource/xml/levels/lvl" + String.valueOf (level) + ".tmx");
		Node map = document.getDocumentElement ();
		additionalCalculates (map);
		
		NodeList objectGroups = map.getChildNodes ();
		for (int i = 0; i < objectGroups.getLength (); i++){
			Node objectGroup = objectGroups.item (i);
			
			if (objectGroup.getNodeType () != Node.TEXT_NODE){
				String currObjectGroup = objectGroup.getAttributes ().item (0).getTextContent (); //запоминаем имя группы
				
				NodeList objects = objectGroup.getChildNodes ();
				for (int j = 0; j < objects.getLength (); j++){
					Node object = objects.item (j);
					
					if (object.getNodeType () != Node.TEXT_NODE){
						parseCoordinates (object);
						
						createObject (currObjectGroup);
					}
				}
			}
		}
	}
}