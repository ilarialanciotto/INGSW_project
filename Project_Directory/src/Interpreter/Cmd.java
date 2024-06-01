package Interpreter;

import GraphicObject.GraphicObject;
import GraphicView.GraphicObjectPanel;

public interface Cmd{
	public void interpret(GraphicObjectPanel gpanel);
}

