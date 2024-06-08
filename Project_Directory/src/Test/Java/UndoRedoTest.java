package Test.Java;

import GraphicObject.GraphicObject;
import GraphicView.GraphicObjectPanel;
import Interpreter.CommandParser;
import Memento.Caretaker;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.*;
import GraphicObject.ID;
import javax.swing.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Order(3)
public class UndoRedoTest {

    private static GraphicObjectPanel gpanel= new GraphicObjectPanel();
    private Caretaker caretaker;
    private JTextField textField;
    private JTextArea textArea;
    private GraphicObject g,g1;
    private int count,countGroup;
    private Point2D oldPosition,oldPosition1;
    private Dimension2D oldDimention,oldDimention1;

    @Before
    public void setUp() {
        caretaker = new Caretaker(gpanel);
        textField = new JTextField();
        textArea = new JTextArea();
    }

    @Test
    @DisplayName("Undo_Redo Create Command")
    public void UndoRedoTestCreateCommand(){
        textField.setText("new rectangle (50.0,50.0) (200.0,200.0)");
        StringReader sr = new StringReader(textField.getText());
        CommandParser cmd = new CommandParser(sr);
        caretaker.executeCommand(cmd.getCommand());
        count=new ID(false).getAllObject().size();

        caretaker.undo();
        assertEquals(count-1,new ID(false).getAllObject().size());
        caretaker.redo();
        assertEquals(count,new ID(false).getAllObject().size());
    }

    @Test
    @DisplayName("Undo_Redo Remove Command")
    public void UndoRedoTestRemoveCommand(){
        textField.setText("del id1");
        StringReader sr = new StringReader(textField.getText());
        CommandParser cmd = new CommandParser(sr);
        caretaker.executeCommand(cmd.getCommand());
        count=new ID(false).getAllObject().size();

        caretaker.undo();
        assertEquals(count+1,new ID(false).getAllObject().size());
        caretaker.redo();
        assertEquals(count,new ID(false).getAllObject().size());
    }

}
