package Interpreter;

import Exception.MyException;
import GraphicObject.GraphicObject;
import GraphicObject.ID;
import GraphicView.GraphicObjectPanel;

public class Move implements Cmd {

	private GraphicObject go;
	private Pos newPos;
	private int ID;
	
	public Move(int objID, Pos pos) {
		go=new ID(false).getObject(objID);
		newPos=new Pos(pos.getX(),pos.getY());
		ID=objID;
	}

	@Override
	public void interpret(GraphicObjectPanel gpanel) {
		if(go==null) 
			if(new ID(false).getGroup(ID)==null ) throw new MyException("object or group not fount");
			else for (GraphicObject goG : new ID(false).getGroup(ID)) goG.moveTo(newPos.getX(), newPos.getY());
		else  go.moveTo(newPos.getX(), newPos.getY());
	}
}
