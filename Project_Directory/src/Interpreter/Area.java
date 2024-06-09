package Interpreter;

import javax.swing.*;

import Exception.MyException;
import GraphicObject.GraphicObject;
import GraphicObject.ID;
import GraphicObject.GraphicObjectPanel;

import java.util.LinkedList;

public class Area implements Cmd {

	private int objID=-1;
	private String type="";
	private float area=0;
	private JTextArea textArea;
	
	public Area(int objID) {
		this.objID=objID;
	}

	public Area(String string) {
		type=string;
	}

	@Override
	public void interpret(GraphicObjectPanel gpanel, JTextArea textArea) {
		this.textArea=textArea;
		GraphicObject go=new ID(false).getObject(objID);
		LinkedList<GraphicObject> map=new ID(false).getGroup(objID);
		if(go!=null) {
			area+=go.Area();
			String input= "Object area " + area + "\n";
			textArea.append(input);
		}
		else if(map!=null && map.size()>0) {
			for (GraphicObject GO: new ID(false).getGroup(objID)) area+=GO.Area();
			String input= "Group area " + area + "\n";
			textArea.append(input);
		}
		else if (type.equalsIgnoreCase("all")) {
			for(GraphicObject GO: new ID(false).getAllObject().values()) area+=GO.Area();	
			String input= "All area " + area + "\n";
			textArea.append(input);
		}
		else if (type.equalsIgnoreCase("rectangle") 
				|| type.equalsIgnoreCase("circle") 
				|| type.equalsIgnoreCase("img")){
			for (GraphicObject GO: new ID(false).getType(type)) area+=GO.Area();
			String input= "Type area " + area + "\n";
			textArea.append(input);
		}
		else throw new MyException ("object ,group or type not found");
	}
}
