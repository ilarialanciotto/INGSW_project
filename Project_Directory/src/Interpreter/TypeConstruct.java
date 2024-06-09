package Interpreter;

import java.awt.geom.Point2D;
import java.io.File;
import javax.swing.*;
import GraphicObject.CircleObject;
import GraphicObject.GraphicObject;
import GraphicObject.ImageObject;
import GraphicObject.RectangleObject;
import GraphicView.GraphicObjectPanel;

public class TypeConstruct implements Cmd {

	private String GoName,path;
	private float radius;
	private Interpreter.Pos date;
	private GraphicObject go;
	
	public TypeConstruct(String GoName, float radius, String path) {
		this.GoName=GoName;
		this.path=path;
		this.radius=radius;
	}

	public TypeConstruct(String goName, Interpreter.Pos pos ){
		this.GoName=goName;
		this.date=new Pos(pos.getX(),pos.getY());
	}

	@Override
	public void interpret(GraphicObjectPanel gpanel, JTextArea textArea) {
		if(GoName.equalsIgnoreCase("rectangle")) { 
			go=new RectangleObject(new Point2D.Float(0,0),date.getX(),date.getY());
		}
		else if(GoName.equalsIgnoreCase("circle")) { 
			go=new CircleObject(new Point2D.Float(0,0),radius);
		}
		else if(GoName.equalsIgnoreCase("img")) {
			if(!new File(path).exists()) throw new IllegalArgumentException("image not found");
			go = new ImageObject(new ImageIcon(path),new Point2D.Float(0,0));
		}
		gpanel.add(go);
	}
	
	

}
