package GraphicObject;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public final class RectangleObject extends AbstractGraphicObject {

	private Point2D position;
	private int Id;
	private Dimension2D dim;
	private float w,h;
	private int IdGroup=-1;
	private Map <Integer,Integer>MapGroup=new TreeMap<>();
	private int i=0;
	
	public RectangleObject(Point2D pos, float w, float h) {
		if (w <= 0 || h <= 0)
			throw new IllegalArgumentException();
		dim = new Dimension();
		dim.setSize(w, h);
		this.w=w;
		this.h=h;
		position = new Point2D.Double(pos.getX(), pos.getY());
		Id=new ID(this).getID(this);
	}

	@Override
	public void drawGraphicObject(GraphicObject go, Graphics2D g) {
		Point2D position = go.getPosition();
		Dimension2D dim = go.getDimension();
		double x = position.getX() - dim.getWidth() / 2.0;
		double y = position.getY() - dim.getHeight() / 2.0;
		g.draw(new Rectangle2D.Double(x, y, dim.getWidth(), dim.getHeight()));

	}
	
	@Override
	public float Area() { return w*h; }
	
	@Override
	public String toString() { return "object id: " + getID() + " type: " + getType(); }
	
	@Override
	public float Perimeter() { return (w+h)*2; }

	@Override
	public boolean contains(Point2D p) {
		double w = dim.getWidth() / 2;
		double h = dim.getHeight() / 2;
		double dx = Math.abs(p.getX() - position.getX());
		double dy = Math.abs(p.getY() - position.getY());
		return dx <= w && dy <= h;
	}

	@Override
	public void moveTo(Point2D p) {
		position.setLocation(p);
		notifyListeners(new GraphicEvent(this));
	}

	@Override
	public Point2D getPosition() {
		return new Point2D.Double(position.getX(), position.getY());
	}

	@Override
	public void scale(double factor) {
		if (factor <= 0)
			throw new IllegalArgumentException();
		dim.setSize(dim.getWidth() * factor, dim.getHeight() * factor);
		notifyListeners(new GraphicEvent(this));
	}

	@Override
	public Dimension2D getDimension() {
		Dimension2D d = new Dimension();
		d.setSize(dim);
		return d;
	}

	@Override
	public String getType() {
		return "Rectangle";
	}

	@Override
	public int getID() { return Id; }

	@Override
	public String Information() {
		return "type: " + getType() + "\n" +
	               "ID: " + getID() + "\n" + 
	               "Dimension: " + getDimension().getWidth() + "," + getDimension().getHeight() + "\n" + 
	               "position: " +  getPosition().getX() + "," + getPosition().getY();
	}

	@Override
	public void setGroupID(int IdGroup) {
		this.IdGroup=IdGroup;
		MapGroup.put(i, IdGroup);
		i++;
	}

	@Override
	public int getIDGroup() { return IdGroup; }

	@Override
	public Map<Integer,Integer> getGroup() { return MapGroup; }

	@Override
	public void setParameter(int id,Map<Integer,Integer> Group){
		this.Id=id;
		this.MapGroup=Group;
	}

	@Override
	public GraphicObject copy() {
		GraphicObject G=this ;
		G.setParameter(Id,MapGroup);
		return G;
	}
	@Override
	public void scaleMinus(float factor) {
		if (factor <= 0)
			throw new IllegalArgumentException();
		dim.setSize(dim.getWidth() / factor, dim.getHeight() / factor);
		notifyListeners(new GraphicEvent(this));
	}
}
