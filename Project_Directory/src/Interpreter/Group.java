package Interpreter;

import java.util.LinkedList;
import Exception.MyException;
import GraphicObject.GraphicObject;
import GraphicObject.ID;
import GraphicView.GraphicObjectPanel;

public class Group implements Cmd {
	
	private LinkedList<GraphicObject>listGraphicObject;
	private int idGroup;
	
	public Group(LinkedList<Integer> listID) {
		listGraphicObject=new LinkedList<>();
		for(Integer id: listID) {
			GraphicObject go =new ID(false).getObject(id);
			LinkedList <GraphicObject> lisGr= new ID(false).getGroup(id);
			if (go!=null) listGraphicObject.add(go);
			else if (lisGr!=null) {
				for(GraphicObject g: lisGr)listGraphicObject.add(g);
			}else throw new MyException("object or group not found");
		}
		new ID(listGraphicObject);
	}
	
	
	@Override
	public void interpret(GraphicObjectPanel gpanel) {
		for (GraphicObject go: listGraphicObject) idGroup=go.getIDGroup();
		System.out.println("Created group id: " + idGroup);
	}

}
