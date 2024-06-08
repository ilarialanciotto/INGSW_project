package Interpreter;

import GraphicObject.GraphicObject;
import GraphicObject.ID;
import GraphicView.GraphicObjectPanel;
import Exception.MyException;
import java.util.LinkedList;

public class Ungroup implements Cmd {

	private int GroupID;
	private static boolean flag=false;
	private GraphicObjectPanel gpanel;
	private LinkedList<Integer> listIDgo=new LinkedList<>();

	public Ungroup(int GroupID) {
		this.GroupID=GroupID;
	}

	@Override
	public void interpret(GraphicObjectPanel gpanel) {
		this.gpanel=gpanel;
		if (flag) GroupID++;
		LinkedList <GraphicObject> map = new ID(false).getGroup(GroupID);
		System.out.println(map + " idG " + GroupID);
		if(map!=null){
			for (GraphicObject Go: map) listIDgo.add(Go.getID());
			new ID(true).getGroup(GroupID);
			System.out.println("Deleted group id: " + GroupID);
			gpanel.setState(this);
		}else throw new MyException("Group not found");
	}

	@Override
	public void undo() {
		flag=true;
		new Group(listIDgo).interpret(gpanel);
	}
}
