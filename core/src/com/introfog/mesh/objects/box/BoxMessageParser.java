package com.introfog.mesh.objects.box;


import com.introfog.mesh.objects.ObjectType;
import com.introfog.mesh.objects.singletons.special.ObjectManager;
import com.introfog.messages.*;

public class BoxMessageParser extends Box{
	private boolean pushOutHorizontal = false;
	private boolean pushOutVertical = false;
	private Box box;
	
	
	private void movedByCharacterMessage (GameMessage message){
		MoveMessage msg = (MoveMessage) message;
		if (msg.deltaX != 0 && box.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY, msg.bodyW, msg.bodyH)){
			ObjectManager.getInstance ().addMessage (
					new MoveMessage (box, msg.deltaX, 0, box.getBodyX (), box.getBodyY (), box.getSpriteX (),
									 box.getSpriteY (), BODY_BOX_W, BODY_BOX_H));
			box.move (msg.deltaX, 0);
		}
		if (msg.deltaY != 0 && box.intersects (msg.oldBodyX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
			ObjectManager.getInstance ().addMessage (
					new MoveMessage (box, 0, msg.deltaY, box.getBodyX (), box.getBodyY (), box.getSpriteX (),
									 box.getSpriteY (), BODY_BOX_W, BODY_BOX_H));
			box.move (0, msg.deltaY);
		}
	}
	
	private void pushOutMessage (GameMessage message){
		//два флага нужны, что бы не было ситуации когда ящик упирается в два объекта, и они его 2 раза выталкивают,
		//вместо одного.
		PushOutMessage msg = (PushOutMessage) message;
		ObjectManager.getInstance ().addMessage (
				new MoveMessage (box, msg.undo.deltaX, msg.undo.deltaY, box.getBodyX (), box.getBodyY (), box.getSpriteX (),
								 box.getSpriteY (), BODY_BOX_W, BODY_BOX_H));
		box.setBodyPosition (msg.undo.oldBodyX, msg.undo.oldBodyY);
	}
	
	
	@Override
	public void update (){
		pushOutHorizontal = false;
		pushOutVertical = false;
	}
	
	public BoxMessageParser (Box box){
		this.box = box;
	}
	
	public boolean parseMessage (GameMessage message){
		if (message.type == MessageType.move && message.objectType == ObjectType.character){
			movedByCharacterMessage (message);
		}
		else if (message.type == MessageType.move && message.objectType == ObjectType.box && message.object != box){
			MoveMessage msg = (MoveMessage) message;
			//не делим на 2 случая по оси Х и У, т.к. ящики двигаются только в 4 направлениях.
			if (box.intersects (msg.oldBodyX + msg.deltaX, msg.oldBodyY + msg.deltaY, msg.bodyW, msg.bodyH)){
				ObjectManager.getInstance ().addMessage (
						new PushOutMessage (msg));
			}
		}
		else if (message.type == MessageType.pushOut && message.object == box){
			pushOutMessage (message);
		}
		return false;
	}
}