package Interpreter;

import Exception.MyException;
import GraphicObject.GraphicObject;
import GraphicObject.ID;
import GraphicObject.GraphicObjectPanel;
import javax.swing.*;
import java.util.LinkedList;

public class Remove implements Cmd {

	private int idObj;
	private GraphicObjectPanel gpanel;
	private GraphicObject go;
	private LinkedList<GraphicObject> copy;
	private LinkedList<GraphicObject> removedList=new LinkedList<>();
	private JTextArea textArea;

	public Remove(int idObj) {
		this.idObj=idObj;
	}

	@Override
	public void interpret(GraphicObjectPanel gpanel, JTextArea textArea) {
		this.textArea=textArea;
		this.gpanel=gpanel;
		go=new ID(false).getObject(idObj);
		if(go==null){
			if(new ID(false).getGroup(idObj)==null) throw new MyException (" object or group not found");
			else {
				LinkedList <GraphicObject> lisGO=  new ID(false).getGroup(idObj) ;
				copy=new LinkedList<>(lisGO);
				for (GraphicObject GO : copy) {
					GraphicObject copyGo=GO.copy();
					removedList.add(copyGo);
					String input= "deleted " + GO.getType() + " id= " + GO.getID() + "\n";
					textArea.append(input);
					new ID(true).getObject(GO.getID());
					gpanel.remove(GO);
				}
			}
		}
		else {
			GraphicObject copyGo=go.copy();
			removedList.add(copyGo);
			String input= "deleted " + go.getType() + " id= " + idObj + "\n";
			textArea.append(input);
			new ID(true).getObject(idObj);
			gpanel.remove(go);
		}
		gpanel.setState(this);
	}

	@Override
	public void undo() {
		for (GraphicObject go : removedList) {
			new ID(false).add(go);
			gpanel.add(go);
		}
	}
}