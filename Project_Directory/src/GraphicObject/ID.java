package GraphicObject;

import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public class ID {
	
	private static int id=0;
	private boolean remove=false;
	private static Map<Integer, GraphicObject> MapGo=new TreeMap<>();
	private static Map<Integer,LinkedList<GraphicObject>> MapGroup=new TreeMap<>();
	private static LinkedList<GraphicObject> TypeList;
	
	public ID(GraphicObject go) {
		MapGo.put(id, go);
		id++;
	}
	
	public Map<Integer, GraphicObject>getAllObject() { return MapGo; }

	public ID(boolean rem) { remove=rem; }

	public ID(LinkedList<GraphicObject> listGroup) {
		MapGroup.put(id, listGroup);
		for(GraphicObject go: listGroup) {
			go.setGroupID(id);
		}
		id++;
	}
	
	public Map<Integer,LinkedList<GraphicObject>> getAllGroup(){ return MapGroup; }
	
	public LinkedList<GraphicObject> getType(String type){
		TypeList=new LinkedList<>();
		for(GraphicObject go: MapGo.values()) if(go.getType().equalsIgnoreCase(type)) TypeList.add(go);
		return TypeList;
	}
	
	public int getID(GraphicObject go) {
		for(Integer id: MapGo.keySet())
			if(MapGo.get(id)==go) return id;		
		return -1;
	}
	
	public GraphicObject getObject(int idObj) {
		GraphicObject res=MapGo.get(idObj);
		if(remove) {
			if(res.getGroup().size()>0){
				for(Integer key: MapGroup.keySet())
					MapGroup.get(key).remove(res);
			}
			MapGo.remove(idObj);
		}
		return res;
	}
	
	public LinkedList<GraphicObject> getGroup(int idGroup) { 
		LinkedList <GraphicObject> Group=MapGroup.get(idGroup);
		if(remove) {
			for (GraphicObject Go: Group) {
				for(Integer key: Go.getGroup().keySet())
					if(Go.getGroup().get(key)==idGroup) Group.remove(key);
			}
			MapGroup.remove(idGroup);
		}
		return Group; 
	}

	public int getGroupID(LinkedList<GraphicObject> listG) {
		for (Integer ID : MapGroup.keySet()) 
			if (MapGroup.get(ID).equals(listG)) return ID;
		return -1;
	}

    public void addObject(int idObj,GraphicObject go,Map<Integer,Integer> Group) {
		MapGo.put(idObj,go);
		if(Group.size()>0)
			for (Integer key: Group.keySet())
				MapGroup.get(Group.get(key)).add(go);
    }
}
