package GraphicView;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JComponent;
import GraphicObject.GraphicEvent;
import GraphicObject.*;
import Interpreter.Cmd;
import Memento.Memento;

public class GraphicObjectPanel extends JComponent implements GraphicObjectListener {

	private Cmd state;
	private static final long serialVersionUID = 8993548105090978185L;
	private final List<GraphicObject> objects = new LinkedList<>();

	public GraphicObjectPanel() {
		setBackground(Color.WHITE);
	}

	@Override
	public void graphicChanged(GraphicEvent e) {
		repaint();
		revalidate();
	}

	public GraphicObject getGraphicObjectAt(Point2D p) {
		for (GraphicObject g : objects) {
			if (g.contains(p))
				return g;
		}
		return null;
	}

	@Override
	public Dimension getPreferredSize() {
		Dimension ps = super.getPreferredSize();
		double x = ps.getWidth();
		double y = ps.getHeight();
		for (GraphicObject go : objects) {
			double nx = go.getPosition().getX() + go.getDimension().getWidth() / 2;
			double ny = go.getPosition().getY() + go.getDimension().getHeight() / 2;
			if (nx > x)
				x = nx;
			if (ny > y)
				y = ny;
		}
		return new Dimension((int) x, (int) y);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		for (GraphicObject go : objects) {
			GraphicObjectView view = GraphicObjectViewFactory.FACTORY.createView(go);
			view.drawGraphicObject(go, g2);
		}
	}

	public void add(GraphicObject go) {
		objects.add(go);
		go.addGraphicObjectListener(this);
		repaint();
	}

	public void remove(GraphicObject go) {
		if (objects.remove(go)) {
			repaint();
			go.removeGraphicObjectListener(this);
		}
	}

	public void setState(Cmd state) {
		this.state = state;
	}

	public Cmd getState() {
		return state;
	}

	public Memento saveToMemento() {
		return new Memento(state);
	}

	public void restoreFromMemento(Memento memento) {
		state = memento.getState();
	}

}
