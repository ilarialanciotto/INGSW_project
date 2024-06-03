package Interpreter;

import Exception.MyException;
import GraphicObject.GraphicObject;
import GraphicObject.ID;
import GraphicView.GraphicObjectPanel;
import java.util.LinkedList;
import java.util.Map;

public class Remove implements Cmd {

	private int idObj;
	private GraphicObjectPanel gpanel;
	private GraphicObject go;
	private int [] list;
	private LinkedList <GraphicObject> copy;
	private Map<Integer,Integer>[] listGroup;
	private Map<Integer,Integer> Group;

	public Remove(int idObj) {
		this.idObj=idObj;
	}

	@Override
	public void interpret(GraphicObjectPanel gpanel) {
		this.gpanel=gpanel;
		go=new ID(false).getObject(idObj);
		if(go==null){
			if(new ID(false).getGroup(idObj)==null) throw new MyException (" object or group not found");
			else {
				list=new int [go.getGroup().size()];
				LinkedList <GraphicObject> lisGO=  new ID(false).getGroup(idObj) ;
				copy=new LinkedList <>(lisGO);
				int i=0;
				for (GraphicObject GO : copy) {
					list[i]=GO.getID();
					listGroup[i]=GO.getGroup();
					System.out.println("deleted " + GO.getType() + " id= " + GO.getID());
					new ID(true).getObject(GO.getID());
					gpanel.remove(GO);
					i++;
				}
			}
		}
		else {
			Group=go.getGroup();
			new ID(true).getObject(idObj);
			System.out.println("deleted " + go.getType() + " id= " + idObj );
			gpanel.remove(go);
		}
		gpanel.setState(this);
	}

	@Override
	public void undo() {
		if(go==null){
			if(new ID(false).getGroup(idObj)==null) throw new MyException (" object or group not found");
			else {
				int i=0;
				for (GraphicObject GO : copy)  {
					new ID(false).addObject(list[i],GO,listGroup[i]);
					gpanel.add(GO);
					i++;
				}
			}
		}
		else {
			new ID(false).addObject(idObj,go,Group);
			gpanel.add(go);
		}
	}
}
