package Interpreter;

import javax.swing.*;

import Exception.MyException;
import GraphicObject.GraphicObject;
import GraphicObject.ID;
import GraphicObject.GraphicObjectPanel;

import java.util.LinkedList;

public class Perimeter implements Cmd {
	
	private int objID=-1;
	private float perimeter=0;
	private String type="";

	public Perimeter(int objID) {
		this.objID=objID;
	}

	public Perimeter(String string) {
		type=string;
	}

	@Override
	public void interpret(GraphicObjectPanel gpanel, JTextArea textArea) {
		GraphicObject go=new ID(false).getObject(objID);
		LinkedList <GraphicObject> map =new ID(false).getGroup(objID);
		if (type.equalsIgnoreCase("all")) {
			for(GraphicObject GO: new ID(false).getAllObject().values()) perimeter+=GO.Perimeter();	
			JOptionPane.showMessageDialog(gpanel, "All perimeter " + perimeter);
		}
		else if (type.equalsIgnoreCase("rectangle") 
				|| type.equalsIgnoreCase("circle") 
				|| type.equalsIgnoreCase("img")){
			for (GraphicObject GO: new ID(false).getType(type)) perimeter+=GO.Perimeter();
			JOptionPane.showMessageDialog(gpanel, "Type perimeter " + perimeter);
		}
		else if(go!=null) {
			perimeter+=go.Perimeter();
			JOptionPane.showMessageDialog(gpanel, "Object perimeter " + perimeter);
		}
		else if(map!=null && map.size()>0) {
			for (GraphicObject GO: new ID(false).getGroup(objID)) perimeter+=GO.Perimeter();
			JOptionPane.showMessageDialog(gpanel, "Group perimeter " + perimeter);
		}
		else throw new MyException ("object , group  or type not found");
	}

}
