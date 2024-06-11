package GraphicObject;


import java.util.LinkedList;
import java.util.List;

public abstract class AbstractGraphicObject implements GraphicObject {

	private  List<GraphicObjectListener> listeners = new LinkedList<>();

	@Override
	public void addGraphicObjectListener(GraphicObjectListener l) {
		if (listeners.contains(l))
			return;
		listeners.add(l);
	}

	@Override
	public void removeGraphicObjectListener(GraphicObjectListener l) {
		listeners.remove(l);
	}

	protected void notifyListeners(GraphicEvent e) {
		for (GraphicObjectListener gol : listeners)
			gol.graphicChanged(e);
	}

}
