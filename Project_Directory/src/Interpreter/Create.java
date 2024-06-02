package Interpreter;

import java.awt.geom.Point2D;
import GraphicObject.GraphicObject;
import GraphicView.GraphicObjectPanel;

public class Create implements Cmd {

	private TypeConstruct typecnstruct;
	private Interpreter.Pos pos;
	
	public Create(TypeConstruct typecnstruct, Pos pos) {
		this.typecnstruct=typecnstruct;
		this.pos=pos;
	}

	@Override
	public void interpret(GraphicObjectPanel gpanel) {
		typecnstruct.interpret(gpanel);
		GraphicObject go=gpanel.getGraphicObjectAt(new Point2D.Float(0,0));
		go.moveTo(pos.getX(), pos.getY());
		gpanel.repaint();
		System.out.println("created object " + go.getType() + " id: " + go.getID());
	}
}
