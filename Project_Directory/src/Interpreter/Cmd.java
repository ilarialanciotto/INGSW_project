package Interpreter;

import GraphicObject.GraphicObjectPanel;

import javax.swing.*;

public interface Cmd {

	public void interpret(GraphicObjectPanel gpanel, JTextArea textArea);

    default void undo(){};
}

