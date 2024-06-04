package Interpreter;

import Exception.MyException;
import GraphicObject.GraphicObject;
import GraphicObject.ID;
import GraphicView.GraphicObjectPanel;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

// controllare perche quando faccio del di un ogetto e poi faccio undo lo reinserisce 2 volte nel gruppo che aveva
// e di conseguenza quando faccio movoff non mi trova gli oggetti del gruppo
// e di conseguenza mi da problemi quando elimino il gruppo

public class Remove implements Cmd {

	private int idObj;
	private GraphicObjectPanel gpanel;
	private GraphicObject go;
	private LinkedList <GraphicObject> copy;
	private LinkedList<GraphicObject> removedList=new LinkedList<>();


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
				LinkedList <GraphicObject> lisGO=  new ID(false).getGroup(idObj) ;
				copy=new LinkedList <>(lisGO);
				for (GraphicObject GO : copy) {
					removedList.add(GO.copy());
					System.out.println("deleted " + GO.getType() + " id= " + GO.getID());
					new ID(true).getObject(GO.getID());
					gpanel.remove(GO);
				}
			}
		}
		else {
			removedList.add(go.copy());
			new ID(true).getObject(idObj);
			System.out.println("deleted " + go.getType() + " id= " + idObj );
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
