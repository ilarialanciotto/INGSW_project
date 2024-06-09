package Interpreter;

import java.util.LinkedList;
import Exception.MyException;
import GraphicObject.GraphicObject;
import GraphicObject.ID;
import GraphicView.GraphicObjectPanel;
import javax.swing.*;

public class Group implements Cmd {
	
	private LinkedList<GraphicObject>listGraphicObject;
	private int idGroup;
	private GraphicObjectPanel gpanel;
	private JTextArea textArea;

	public Group(LinkedList<Integer> listID) {
		listGraphicObject=new LinkedList<>();
		for(Integer id: listID) {
			GraphicObject go =new ID(false).getObject(id);
			LinkedList <GraphicObject> lisGr= new ID(false).getGroup(id);
			if (go!=null) listGraphicObject.add(go);
			else if(lisGr!=null) for(GraphicObject g: lisGr) listGraphicObject.add(g);
			else throw new MyException("object or group not found");
		}
	}

	@Override
	public void interpret(GraphicObjectPanel gpanel, JTextArea textArea) {
		this.textArea=textArea;
		new ID(listGraphicObject);
		this.gpanel=gpanel;
		for (GraphicObject go : listGraphicObject) idGroup = go.getIDGroup();
		String input="Created group id: " + idGroup + "\n";
		textArea.append(input);
		gpanel.setState(this);
	}

	@Override
	public void undo() {
		new Ungroup(idGroup).interpret(gpanel, textArea);
	}
}
