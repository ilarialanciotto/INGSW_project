package Interpreter;

import GraphicObject.ID;
import GraphicView.GraphicObjectPanel;

public class Ungroup implements Cmd {

	private int GroupID;
	
	public Ungroup(int GroupID) {
		this.GroupID=GroupID;
	}

	@Override
	public void interpret(GraphicObjectPanel gpanel) {
	    new ID(true).getGroup(GroupID);
		System.out.println("Deleted group id: " + GroupID);
	}

}
