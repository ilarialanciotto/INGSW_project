package Interpreter;

import java.util.LinkedList;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Exception.MyException;
import GraphicObject.GraphicObject;
import GraphicObject.ID;
import GraphicView.GraphicObjectPanel;

public class List implements Cmd {
	
	private int objID=-1;
	private String mode="";
	private String info="";
	
	public List(int objID) {
		this.objID=objID;
	}

	public List(String string) { 
		mode=string;
	}
	
	@Override
	public void interpret(GraphicObjectPanel gpanel) {
		GraphicObject go=new ID(false).getObject(objID);
		if(go!=null) {
			info=go.Information();
			JOptionPane.showMessageDialog(gpanel, info);
		}
		else if (new ID(false).getGroup(objID)!=null) {
			info="Group id " + objID + "\n";
			for(GraphicObject GO: new ID(false).getGroup(objID)) info=info+GO.toString()+"\n";	
			JOptionPane.showMessageDialog(gpanel, info);	
		}
		else if (mode.equalsIgnoreCase("all")) {
			for(GraphicObject GO: new ID(false).getAllObject().values()) info=info+GO.toString()+"\n";	
			JOptionPane.showMessageDialog(gpanel, info);
		}
		else if (mode.equalsIgnoreCase("rectangle") 
				|| mode.equalsIgnoreCase("circle") 
				|| mode.equalsIgnoreCase("img")){
			for (GraphicObject GO: new ID(false).getType(mode)) info=info+GO.toString()+"\n";
			JOptionPane.showMessageDialog(gpanel, info);
		}
		else if(mode.equalsIgnoreCase("groups")) {
			for(LinkedList<GraphicObject> lisGO: new ID(false).getAllGroup().values()) {
				info=info + "Group id " + new ID(false).getGroupID(lisGO) + "\n";
				for(GraphicObject GO: lisGO) info=info+GO.toString()+"\n";
			}
			JOptionPane.showMessageDialog(gpanel, info);
		}
		else throw new MyException ("object or type not found");
	}

}