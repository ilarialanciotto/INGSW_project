package Interpreter;

import Exception.MyException;
import GraphicObject.GraphicObject;
import GraphicObject.ID;
import GraphicView.GraphicObjectPanel;

public class MoveOff implements Cmd {
	
	private GraphicObject go;
	private Pos newPos;
	private int ID;
	private double x,y;

	public MoveOff(int objID, Pos pos) {
		go=new ID(false).getObject(objID);
		newPos=new Pos(pos.getX(),pos.getY());
		ID=objID;
	}

	@Override
	public void interpret(GraphicObjectPanel gpanel) {
		if(go!=null) {
			x=go.getPosition().getX();
			y=go.getPosition().getY();
			go.moveTo(newPos.getX()+x, newPos.getY()+y);
		}
		else if(new ID(false).getGroup(ID)!=null )
			for (GraphicObject goG : new ID(false).getGroup(ID)) {
				x=goG.getPosition().getX();
				y=goG.getPosition().getY();
				goG.moveTo(newPos.getX()+x, newPos.getY()+y);
			}
		else throw new MyException("object or group not fount");
	}



}

