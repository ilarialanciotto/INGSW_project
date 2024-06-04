package Test.Java;

import GraphicView.GraphicObjectPanel;
import Interpreter.*;
import Memento.Caretaker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import GraphicObject.GraphicObject;
import javax.swing.*;
import java.awt.geom.Point2D;
import java.io.StringReader;
import java.util.LinkedList;
import static org.junit.Assert.assertEquals;

public class MoveCommandTest {

    private Caretaker caretaker;
    private GraphicObjectPanel gpanel;
    private JTextField textField;
    private JTextArea textArea;

    @BeforeEach
    public void setUp() {
        gpanel = new GraphicObjectPanel();
        caretaker = new Caretaker(gpanel);
        textField = new JTextField();
        textArea = new JTextArea();
    }

    @Test
    @DisplayName("Move Command Test")
    public void TestRemoveCommand(){
        new Create( new TypeConstruct("circle",20,null),new Pos(100,100)).interpret(gpanel);
        textField.setText("mv 3 (200,200)");
        GraphicObject go=gpanel.getGraphicObjectAt(new Point2D.Double(100,100));
        StringReader sr = new StringReader(textField.getText());
        CommandParser cmd = new CommandParser(sr);
        caretaker.executeCommand(cmd.getCommand());
        assertEquals(new Point2D.Double(200,200),go.getPosition());
    }

    @Test
    @DisplayName("Move Group command test")
    public void TestRemoveGroupCommand(){
        new Create( new TypeConstruct("circle",20,null),new Pos(100,100)).interpret(gpanel);
        new Create( new TypeConstruct("circle",30,null),new Pos(200,200)).interpret(gpanel);
        LinkedList<Integer> ids = new LinkedList<>(); ids.add(0); ids.add(1);
        new Group(ids);

        GraphicObject go1=gpanel.getGraphicObjectAt(new Point2D.Double(100,100));
        GraphicObject go2=gpanel.getGraphicObjectAt(new Point2D.Double(200,200));

        textField.setText("mv 2 (300,300)");
        StringReader sr = new StringReader(textField.getText());
        CommandParser cmd = new CommandParser(sr);
        caretaker.executeCommand(cmd.getCommand());
        assertEquals(new Point2D.Double(300,300),go1.getPosition());
        assertEquals(new Point2D.Double(300,300),go2.getPosition());
    }
}
