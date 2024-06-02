package Interpreter;

import Exception.MyException;
import GraphicObject.GraphicObject;
import GraphicObject.ID;
import GraphicView.GraphicObjectPanel;
import java.util.LinkedList;

public class Remove implements Cmd {

	private int idObj;

	public Remove(int idObj) {
		this.idObj=idObj;
	}

	@Override
	public void interpret(GraphicObjectPanel gpanel) {
		GraphicObject go=new ID(false).getObject(idObj);
		if(go==null){
			if(new ID(false).getGroup(idObj)==null) throw new MyException (" object or group not found");
			else {
				LinkedList <GraphicObject> lisGO=  new ID(false).getGroup(idObj) ;
				LinkedList <GraphicObject> copy=new LinkedList <>(lisGO);
				for (GraphicObject GO : copy) {
					System.out.println("deleted " + GO.getType() + " id= " + GO.getID());
					new ID(true).getObject(GO.getID());
					gpanel.remove(GO);
				}
			}
		}
		else {
			new ID(true).getObject(idObj);
			System.out.println("deleted " + go.getType() + " id= " + idObj );
			gpanel.remove(go);
		}
	}
}
