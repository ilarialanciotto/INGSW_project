package Interpreter;

import java.awt.geom.Point2D;
import GraphicObject.GraphicObject;
import GraphicView.GraphicObjectPanel;
import javax.swing.*;

public class Create implements Cmd {

	private TypeConstruct typecnstruct;
	private Interpreter.Pos pos;
	private GraphicObject go;
	private GraphicObjectPanel gpanel;
	private JTextArea textArea;
	
	public Create(TypeConstruct typecnstruct, Pos pos) {
		this.typecnstruct=typecnstruct;
		this.pos=pos;
	}

	@Override
	public void interpret(GraphicObjectPanel gpanel, JTextArea textArea) {
		this.gpanel=gpanel;
		this.textArea=textArea;
		typecnstruct.interpret(gpanel, textArea);
		go=gpanel.getGraphicObjectAt(new Point2D.Float(0,0));
		go.moveTo(pos.getX(), pos.getY());
		gpanel.repaint();
		gpanel.setState(this);
		String input= "created object " + go.getType() + " id: " + go.getID() + "\n";
		textArea.append(input);
	}

	@Override
	public void undo() {
		new Remove(go.getID()).interpret(gpanel, textArea);
	}
}
