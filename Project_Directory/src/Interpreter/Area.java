package Interpreter;

import javax.swing.*;
import Exception.MyException;
import GraphicObject.GraphicObject;
import GraphicObject.ID;
import GraphicView.GraphicObjectPanel;
import java.util.LinkedList;

public class Area implements Cmd {

	private int objID=-1;
	private String type="";
	private float area=0;
	
	public Area(int objID) {
		this.objID=objID;
	}

	public Area(String string) {
		type=string;
	}

	@Override
	public void interpret(GraphicObjectPanel gpanel, JTextArea textArea) {
		GraphicObject go=new ID(false).getObject(objID);
		LinkedList<GraphicObject> map=new ID(false).getGroup(objID);
		if(go!=null) {
			area+=go.Area();
			JOptionPane.showMessageDialog(gpanel, "Object area " + area);
		}
		else if(map!=null && map.size()>0) {
			for (GraphicObject GO: new ID(false).getGroup(objID)) area+=GO.Area();
			JOptionPane.showMessageDialog(gpanel, "Group area " + area);
		}
		else if (type.equalsIgnoreCase("all")) {
			for(GraphicObject GO: new ID(false).getAllObject().values()) area+=GO.Area();	
			JOptionPane.showMessageDialog(gpanel, "All area " + area);
		}
		else if (type.equalsIgnoreCase("rectangle") 
				|| type.equalsIgnoreCase("circle") 
				|| type.equalsIgnoreCase("img")){
			for (GraphicObject GO: new ID(false).getType(type)) area+=GO.Area();
			JOptionPane.showMessageDialog(gpanel, "Type area " + area);
		}
		else throw new MyException ("object ,group or type not found");
	}
}
