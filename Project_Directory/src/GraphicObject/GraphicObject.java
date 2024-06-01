package GraphicObject;

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.Map;

public interface GraphicObject {
	
	int getID();

	void addGraphicObjectListener(GraphicObjectListener l);

	void removeGraphicObjectListener(GraphicObjectListener l);

	void moveTo(Point2D p);

	default void moveTo(double x, double y){
		moveTo(new Point2D.Double(x, y));
	}

	Point2D getPosition();

	Dimension2D getDimension();

	void scale(double factor);

	boolean contains(Point2D p);

	float Area();
	
	float Perimeter();
	
	String Information();
	
	void setGroupID(int IdGroup);
	
	int getIDGroup();
	
	@Override
	String toString();
	
	String getType();
	
	Map<Integer,Integer> getGroup();

}
