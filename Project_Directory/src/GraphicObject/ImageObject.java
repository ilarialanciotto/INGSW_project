package GraphicObject;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.ImageIcon;

import Interpreter.Pos;

public final class ImageObject extends AbstractGraphicObject {
	
	private double factor = 1.0;
	private final Image image;
	private Point2D position;
	private int Id;
	private int IdGroup=-1;
	private Map <Integer,Integer>MapGroup=new TreeMap<>();
	private int i=0;
	
	public Image getImage() {
		return image;
	}

	public ImageObject(ImageIcon img, Point2D pos) {
		position = new Point2D.Double(pos.getX(), pos.getY());
		image = img.getImage();
		Id=new ID(this).getID(this);
	}
	
	@Override
	public float Area() { return image.getWidth(null)*image.getHeight(null); }
	
	@Override
	public String toString() { return "object id: " + getID() + " type: " + getType(); }
	
	@Override
	public float Perimeter() { return (image.getWidth(null)+image.getHeight(null))*2; }

	@Override
	public boolean contains(Point2D p) {
		double w = (factor * image.getWidth(null)) / 2;
		double h = (factor * image.getHeight(null)) / 2;
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
	public ImageObject clone() {
		ImageObject cloned = (ImageObject) super.clone();
		cloned.position = (Point2D) position.clone();
		return cloned;
	}

	@Override
	public Point2D getPosition() {
		return new Point2D.Double(position.getX(), position.getY());
	}

	@Override
	public void scale(double factor) {
		if (factor <= 0)
			throw new IllegalArgumentException();
		this.factor *= factor;
		notifyListeners(new GraphicEvent(this));
	}

	@Override
	public void scaleMinus(float factor) {
		if (factor <= 0)
			throw new IllegalArgumentException();
		this.factor /= factor;
		notifyListeners(new GraphicEvent(this));
	}

	@Override
	public Dimension2D getDimension() {
		Dimension dim = new Dimension();
		dim.setSize(factor * image.getWidth(null),
				factor * image.getHeight(null));
		return dim;
	}

	@Override
	public String getType() {
		return "img";
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
	public void setGroupID(int IdGroup) {
		this.IdGroup=IdGroup;
		MapGroup.put(i, IdGroup);
	}

	@Override
	public int getIDGroup() { return IdGroup; }

	@Override
	public Map<Integer,Integer> getGroup() { return MapGroup; }
}
