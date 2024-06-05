package Test.Java;

import GraphicView.GraphicObjectPanel;
import Interpreter.*;
import Memento.Caretaker;
import org.junit.Before;
import GraphicObject.ID;
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
        new Create( new TypeConstruct("circle",20,null),new Pos(150,100)).interpret(gpanel);
        GraphicObject g=gpanel.getGraphicObjectAt(new Point2D.Double(150,100));

        textField.setText("mv " + g.getID() + " (100,250)");
        StringReader sr = new StringReader(textField.getText());
        CommandParser cmd = new CommandParser(sr);
        caretaker.executeCommand(cmd.getCommand());
        assertEquals(new Point2D.Double(100,250),g.getPosition());
    }

    @Test
    @DisplayName("Move Group command test")
    public void TestRemoveGroupCommand(){
        new Create( new TypeConstruct("circle",20,null),new Pos(100,100)).interpret(gpanel);
        new Create( new TypeConstruct("circle",30,null),new Pos(200,200)).interpret(gpanel);
        GraphicObject go1=gpanel.getGraphicObjectAt(new Point2D.Double(100,100));
        GraphicObject go2=gpanel.getGraphicObjectAt(new Point2D.Double(200,200));
        LinkedList<Integer> idL = new LinkedList<>(); idL.add(go1.getID()); idL.add(go2.getID());
        new Group(idL).interpret(gpanel);
        LinkedList<GraphicObject> glist = new LinkedList<>();
        glist.add(go1); glist.add(go2);

        textField.setText("mv "+ new ID(false).getGroupID(glist) + " (300,300)");
        StringReader sr = new StringReader(textField.getText());
        CommandParser cmd = new CommandParser(sr);
        caretaker.executeCommand(cmd.getCommand());
        assertEquals(new Point2D.Double(300,300),go1.getPosition());
        assertEquals(new Point2D.Double(300,300),go2.getPosition());
    }
}
