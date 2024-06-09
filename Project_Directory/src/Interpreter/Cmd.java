package Interpreter;

import GraphicView.GraphicObjectPanel;
import javax.swing.*;

public interface Cmd {

	void interpret(GraphicObjectPanel gpanel, JTextArea textArea);

    default void undo(){};

}

