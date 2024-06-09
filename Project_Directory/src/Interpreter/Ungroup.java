package Interpreter;

import GraphicObject.GraphicObject;
import GraphicObject.ID;
import GraphicView.GraphicObjectPanel;
import Exception.MyException;
import javax.swing.*;
import java.util.LinkedList;

public class Ungroup implements Cmd {

	private int GroupID;
	private static boolean flag=false;
	private GraphicObjectPanel gpanel;
	private LinkedList<Integer> listIDgo=new LinkedList<>();
	private JTextArea textArea;

	public Ungroup(int GroupID) {
		this.GroupID=GroupID;
	}

	@Override
	public void interpret(GraphicObjectPanel gpanel, JTextArea textArea) {
		this.textArea=textArea;
		this.gpanel=gpanel;
		if (flag) GroupID++;
		LinkedList <GraphicObject> map = new ID(false).getGroup(GroupID);
		if(map!=null){
			for (GraphicObject Go: map) listIDgo.add(Go.getID());
			new ID(true).getGroup(GroupID);
			String input="Deleted group id: " + GroupID + "\n";
			textArea.append(input);
			gpanel.setState(this);
		}else throw new MyException("Group not found");
	}

	@Override
	public void undo() {
		flag=true;
		new Group(listIDgo).interpret(gpanel, textArea);
	}
}
