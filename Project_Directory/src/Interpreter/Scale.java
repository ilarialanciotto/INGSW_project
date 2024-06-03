package Interpreter;

import Exception.MyException;
import GraphicObject.GraphicObject;
import GraphicObject.ID;
import GraphicView.GraphicObjectPanel;

public class Scale implements Cmd {
	
	private GraphicObject go;
	private float factor;
	private int ID;

	public Scale(int objID, float posfloat) {
		go=new ID(false).getObject(objID);
		factor=posfloat;
		ID=objID;
	}

	@Override
	public void interpret(GraphicObjectPanel gpanel) {
		if(go==null) 
			if(new ID(false).getGroup(ID)==null ) throw new MyException("object or group not fount");
			else for (GraphicObject goG : new ID(false).getGroup(ID)) goG.scale(factor);
		else  go.scale(factor);
		gpanel.setState(this);
	}

	@Override
	public void undo() {
		if(go==null)
			if(new ID(false).getGroup(ID)==null ) throw new MyException("object or group not fount");
			else for (GraphicObject goG : new ID(false).getGroup(ID)) goG.scaleMinus(factor);
		else  go.scaleMinus(factor);
	}
}
