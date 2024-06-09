package Interpreter;

import Exception.MyException;
import GraphicObject.GraphicObject;
import GraphicObject.ID;
import GraphicView.GraphicObjectPanel;
import javax.swing.*;

public class MoveOff implements Cmd {
	
	private GraphicObject go;
	private int ID;
	private double x,y;

	public MoveOff(int objID, Pos pos) {
		go=new ID(false).getObject(objID);
		x= pos.getX() ;
		y=pos.getY();
		ID=objID;
	}

	@Override
	public void interpret(GraphicObjectPanel gpanel, JTextArea textArea) {
		if(go!=null)
			go.moveTo(go.getPosition().getX()+ x, go.getPosition().getY()+ y);
		else if(new ID(false).getGroup(ID)!=null )
			for (GraphicObject goG : new ID(false).getGroup(ID))
				goG.moveTo(goG.getPosition().getX() + x, goG.getPosition().getY()+ y);
		else throw new MyException("object or group not fount");
		gpanel.setState(this);
	}

	@Override
	public void undo() {
		if(go!=null)
			go.moveTo(go.getPosition().getX()- x, go.getPosition().getY()- y);
		else if(new ID(false).getGroup(ID)!=null )
			for (GraphicObject goG : new ID(false).getGroup(ID))
				goG.moveTo(goG.getPosition().getX()- x, goG.getPosition().getY()- y);
		else throw new MyException("object or group not fount");
	}
}

