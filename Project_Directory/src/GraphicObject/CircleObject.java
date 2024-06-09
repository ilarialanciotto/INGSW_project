package GraphicObject;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public final  class CircleObject extends AbstractGraphicObject {

	private Point2D position;
	private float radius;
	private int Id;
	private int GroupID=-1;
	private Map <Integer,Integer>MapGroup=new TreeMap<>();
	private int i=0;
	
	public CircleObject(Point2D pos, float r) {
		if (r <= 0)
			throw new IllegalArgumentException();
		position = new Point2D.Double(pos.getX(), pos.getY());
		radius = r;
		Id=new ID(this).getID(this);
	}
	
	@Override
	public void moveTo(Point2D p) {
		position.setLocation(p);
		notifyListeners(new GraphicEvent(this));
	}
	
	@Override
	public String toString() { return "object id: " + getID()+ " type: " + getType(); }
	
	@Override
	public float Perimeter() { return (float) (2*radius*Math.PI); }
	
	@Override 
	public float Area() { return (float)(Math.pow(radius, 2)*Math.PI); }

	@Override
	public Point2D getPosition() {
		return new Point2D.Double(position.getX(), position.getY());
	}

	@Override
	public void scale(double factor) {
		if (factor <= 0)
			throw new IllegalArgumentException();
		radius *= factor;
		notifyListeners(new GraphicEvent(this));
	}

	@Override
	public void scaleMinus(float factor) {
		if (factor <= 0)
			throw new IllegalArgumentException();
		radius /= factor;
		notifyListeners(new GraphicEvent(this));
	}


	@Override
	public Dimension2D getDimension() {
		Dimension d = new Dimension();
		d.setSize(2 * radius, 2 * radius);
		return d;
	}

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
	public boolean contains(Point2D p) {
		return (position.distance(p) <= radius);
	}

	@Override
	public CircleObject clone() {
		CircleObject cloned = (CircleObject) super.clone();
		cloned.position = (Point2D) position.clone();
		return cloned;
	}

	@Override
	public String getType() {
		return "Circle";
	}

	public double getRadius() {
		return radius;
	}

	@Override
	public int getID() {
		return Id;
	}

	@Override
	public String Information() {
		return "type: " + getType() + "\n" +
	               "ID: " + getID() + "\n" + 
	               "radius: " + getRadius() + "\n" + 
	               "position: " +  getPosition().getX() + "," + getPosition().getY();
	}

	@Override
	public void setGroupID(int idGroup) {
		this.GroupID=idGroup;
		MapGroup.put(i, idGroup);
		i++;
	}

	@Override
	public int getIDGroup() { return GroupID; }

	@Override
	public Map<Integer,Integer> getGroup() { return MapGroup; }
}
