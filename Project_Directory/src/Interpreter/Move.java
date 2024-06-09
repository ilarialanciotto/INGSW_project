package Interpreter;

import Exception.MyException;
import GraphicObject.GraphicObject;
import GraphicObject.ID;
import GraphicObject.GraphicObjectPanel;

import javax.swing.*;
import java.awt.geom.Point2D;

public class Move implements Cmd {

	private GraphicObject go;
	private Pos newPos;
	private int ID;
	private Point2D [] ListPos;
	private Point2D oldPos;
	
	public Move(int objID, Pos pos) {
		go=new ID(false).getObject(objID);
		newPos=new Pos(pos.getX(),pos.getY());
		ID=objID;
	}

	@Override
	public void interpret(GraphicObjectPanel gpanel, JTextArea textArea) {
		if(go==null) 
			if(new ID(false).getGroup(ID)==null ) throw new MyException("object or group not fount");
			else {
				ListPos=new Point2D [new ID(false).getGroup(ID).size()];
				int i=0;
				for (GraphicObject goG : new ID(false).getGroup(ID)) {
					ListPos[i]= goG.getPosition();
					i++;
					goG.moveTo(newPos.getX(), newPos.getY());
				}
			}
		else  {
			oldPos=go.getPosition();
			go.moveTo(newPos.getX(), newPos.getY());
		}
		gpanel.setState(this);
	}

	@Override
	public void undo() {
		if(go==null)
			if(new ID(false).getGroup(ID)==null ) throw new MyException("object or group not fount");
			else {
				int j=0;
				for (GraphicObject goG : new ID(false).getGroup(ID)) {
					goG.moveTo(ListPos[j]);
					j++;
				}
			}
		else  {
			go.moveTo(oldPos);
		}
	}
}
