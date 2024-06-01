package Interpreter;

import Exception.MyException;
import GraphicObject.GraphicObject;
import GraphicObject.ID;
import GraphicView.GraphicObjectPanel;

public class Remove implements Cmd {

	private int idObj;
	
	public Remove(int idObj) {
		this.idObj=idObj;
	}

	@Override
	public void interpret(GraphicObjectPanel gpanel) {
		GraphicObject go=new ID(false).getObject(idObj);
		if(go==null) {
			if(new ID(false).getGroup(idObj)==null ) throw new MyException("object or group not fount");
			else {
				for (GraphicObject goG : new ID(false).getGroup(idObj)) { 
					System.out.println("deleted " + goG.getType() + " id= " + goG.getID());
					new ID(true).getObject(goG.getID());
					gpanel.remove(goG);
				}
				new ID(true).getGroup(idObj);
			}
		} else  {
			while(go.getGroup().size()>0) {
				for (Integer key : go.getGroup().keySet()) {
					new ID(false).getGroup(go.getGroup().get(key)).remove(go);
					go.getGroup().remove(key);
				}
			}
			new ID(true).getObject(idObj);
			System.out.println("deleted " + go.getType() + " id= " + go.getID());
			gpanel.remove(go);		
		}
	}
}
