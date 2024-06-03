package Interpreter;

import GraphicObject.GraphicObject;
import GraphicObject.ID;
import GraphicView.GraphicObjectPanel;

import java.util.LinkedList;

public class Ungroup implements Cmd {

	private int GroupID;
	private GraphicObjectPanel gpanel;
	private LinkedList<Integer> listIDgo=new LinkedList<>();

	public Ungroup(int GroupID) {
		this.GroupID=GroupID;
	}

	@Override
	public void interpret(GraphicObjectPanel gpanel) {
		this.gpanel=gpanel;
		for (GraphicObject Go: new ID(false).getGroup(GroupID)){
			listIDgo.add(Go.getID());
		}
		System.out.println(listIDgo);
	    new ID(true).getGroup(GroupID);
		System.out.println("Deleted group id: " + GroupID);
		gpanel.setState(this);
	}

	@Override
	public void undo() {
		new Group(listIDgo).interpret(gpanel);
	}
}
