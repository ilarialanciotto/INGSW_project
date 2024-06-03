package Interpreter;

import java.awt.geom.Point2D;
import GraphicObject.GraphicObject;
import GraphicView.GraphicObjectPanel;

public class Create implements Cmd {

	private TypeConstruct typecnstruct;
	private Interpreter.Pos pos;
	private GraphicObject go;
	private GraphicObjectPanel gpanel;
	
	public Create(TypeConstruct typecnstruct, Pos pos) {
		this.typecnstruct=typecnstruct;
		this.pos=pos;
	}

	@Override
	public void interpret(GraphicObjectPanel gpanel) {
		this.gpanel=gpanel;
		typecnstruct.interpret(gpanel);
		go=gpanel.getGraphicObjectAt(new Point2D.Float(0,0));
		go.moveTo(pos.getX(), pos.getY());
		gpanel.repaint();
		gpanel.setState(this);
		System.out.println("created object " + go.getType() + " id: " + go.getID());
	}

	@Override
	public void undo() {
		new Remove(go.getID()).interpret(gpanel);
	}
}
