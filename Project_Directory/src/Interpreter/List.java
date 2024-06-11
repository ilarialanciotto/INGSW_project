package Interpreter;

import java.util.LinkedList;

import javax.swing.*;

import Exception.MyException;
import GraphicObject.GraphicObject;
import GraphicObject.ID;
import GraphicObject.GraphicObjectPanel;

public class List implements Cmd {
	
	private int objID=-1;
	private String mode="";
	private String info;
	private static JTextArea textArea=new JTextArea();

	public List(){}
	
	public List(int objID) {
		this.objID=objID;
	}

	public List(String string) { 
		mode=string;
	}
	
	@Override
	public void interpret(GraphicObjectPanel gpanel, JTextArea textArea) {
		this.textArea=textArea;
		info="";
		GraphicObject go=new ID(false).getObject(objID);
		if(go!=null)
			textArea.append(go.Information() + "\n");
		else if (new ID(false).getGroup(objID)!=null) {
			info="Group id " + objID + "\n";
			for(GraphicObject GO: new ID(false).getGroup(objID)) info=info+GO.toString()+"\n";
		}
		else if (mode.equalsIgnoreCase("all"))
			for(GraphicObject GO: new ID(false).getAllObject().values()) info=info+GO.toString()+"\n";
		else if (mode.equalsIgnoreCase("rectangle") 
				|| mode.equalsIgnoreCase("circle") 
				|| mode.equalsIgnoreCase("img")){
			for (GraphicObject GO: new ID(false).getType(mode)) info=info+GO.toString()+"\n";
		}
		else if(mode.equalsIgnoreCase("groups")) {
			for(LinkedList<GraphicObject> lisGO: new ID(false).getAllGroup().values()) {
				info=info + "Group id " + new ID(false).getGroupID(lisGO) + "\n";
				for(GraphicObject GO: lisGO) info=info+GO.toString()+"\n";
			}
		}
		else throw new MyException ("object or type not found");
		textArea.append(info);
	}

	public String getInfoShow() { return textArea.getText(); }

}
