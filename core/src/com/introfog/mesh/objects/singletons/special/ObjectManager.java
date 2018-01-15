package com.introfog.mesh.objects.singletons.special;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import com.introfog.mesh.objects.*;
import com.introfog.mesh.objects.box.Box;
import com.introfog.messages.*;
import com.introfog.render.Render;

import java.util.LinkedList;

public class ObjectManager implements GameObject{
	private LinkedList <GameMessage> messages;
	private LinkedList <GameMessage> messageLink;
	private LinkedList <GameObject> objects;
	
	
	private static class ObjectManagerHolder{
		private final static ObjectManager instance = new ObjectManager ();
	}
	
	private ObjectManager (){
		messages = new LinkedList <> ();
		messageLink = new LinkedList <> ();
		objects = new LinkedList <> ();
		
		Pools.set (Wall.class, new Pool <Wall> (100, 400){
			@Override
			protected Wall newObject (){
				return new Wall ();
			}
		});
		Pools.set (Box.class, new Pool <Box> (15, 30){
			@Override
			protected Box newObject (){
				return new Box (true);
			}
		});
		Pools.set (FinishLevel.class, new Pool <FinishLevel> (15, 30){
			@Override
			protected FinishLevel newObject (){
				return new FinishLevel ();
			}
		});
		Pools.set (InvisibleWall.class, new Pool <InvisibleWall> (15, 30){
			@Override
			protected InvisibleWall newObject (){
				return new InvisibleWall ();
			}
		});
		
		Pools.set (AddObjectMessage.class, new Pool <AddObjectMessage> (20, 100){
			@Override
			protected AddObjectMessage newObject (){
				return new AddObjectMessage ();
			}
		});
		Pools.set (CompleteLevelMessage.class, new Pool <CompleteLevelMessage> (1, 5){
			@Override
			protected CompleteLevelMessage newObject (){
				return new CompleteLevelMessage ();
			}
		});
		Pools.set (MoveMessage.class, new Pool <MoveMessage> (16, 31){
			@Override
			protected MoveMessage newObject (){
				return new MoveMessage ();
			}
		});
		Pools.set (PushOutMessage.class, new Pool <PushOutMessage> (3, 5){
			@Override
			protected PushOutMessage newObject (){
				return new PushOutMessage ();
			}
		});
		Pools.set (PlayerLostMessage.class, new Pool <PlayerLostMessage> (1, 2){
			@Override
			protected PlayerLostMessage newObject (){
				return new PlayerLostMessage ();
			}
		});
		Pools.set (DestroyObjectMessage.class, new Pool <DestroyObjectMessage> (1, 2){
			@Override
			protected DestroyObjectMessage newObject (){
				return new DestroyObjectMessage ();
			}
		});
	}
	
	
	public static ObjectManager getInstance (){
		return ObjectManagerHolder.instance;
	}
	
	@Override
	public void update (){
		//проверка на пустоту обязательно, т.к. может быть ситуация когда обновляется LevelManager и игрок нажимает на
		//на Escape и происходит очищение всех объектов в object, а i = object.size (), до удаления.
		for (int i = objects.size () - 1; i > -1 && !objects.isEmpty (); i--){
			objects.get (i).update ();
		}
		
		while (!messages.isEmpty ()){
			GameMessage msg = messages.remove ();
			messageLink.add (msg);
			
			for (int i = objects.size () - 1; i > -1 && !objects.isEmpty (); i--){
				if (objects.get (i).sendMessage (msg)){
					break;
				}
			}
		}
		while (!messageLink.isEmpty ()){
			Pools.free (messageLink.remove ());
		}
	}
	
	@Override
	public boolean sendMessage (GameMessage message){
		if (message.type == MessageType.addObject){
			objects.add (message.object);
			Pools.free (message);
			return true;
		}
		return false;
	}
	
	@Override
	public void draw (){
		for (GameObject obj : objects){
			obj.draw ();
		}
		Render.getInstance ().renderScene ();
	}
	
	@Override
	public void clear (){
		//метод вызывается при закрытии уровня, или перехода к следующему, и служит для очистки перменных классов.
		for (GameObject obj : objects){
			obj.clear ();
		}
		messages.clear ();
		objects.clear ();
	}
	
	public void addMessage (GameMessage msg){
		messages.add (msg);
	}
}