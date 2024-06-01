package GraphicView;

import java.awt.Graphics2D;
import GraphicObject.GraphicObject;

public interface GraphicObjectView {
	void drawGraphicObject(GraphicObject go, Graphics2D g);
}
