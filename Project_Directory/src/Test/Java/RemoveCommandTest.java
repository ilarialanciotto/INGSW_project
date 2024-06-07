package Test.Java;

import static org.junit.Assert.*;
import GraphicObject.GraphicObject;
import GraphicObject.ID;
import GraphicView.GraphicObjectPanel;
import Interpreter.*;
import Memento.Caretaker;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.*;
import java.io.StringReader;
import java.util.LinkedList;

public class RemoveCommandTest {

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
    @DisplayName("Remove command test")
    public void TestRemoveCommand(){
        new Create( new TypeConstruct("circle",20,null),new Pos(300,300)).interpret(gpanel);
        GraphicObject go= gpanel.getGraphicObjectAt(new Point(300,300));
        textField.setText("del " + go.getID());
        StringReader sr = new StringReader(textField.getText());
        CommandParser cmd = new CommandParser(sr);
        int count=gpanel.getObjectCount();
        caretaker.executeCommand(cmd.getCommand());
        assertEquals(count-1,gpanel.getObjectCount());
        assertEquals(gpanel.getObjectCount(),new ID(false).getAllObject().size());
    }

    @Test
    @DisplayName("Remove Group command test")
    public void TestRemoveGroupCommand(){
        new Create( new TypeConstruct("circle",20,null),new Pos(100,100)).interpret(gpanel);
        new Create( new TypeConstruct("circle",30,null),new Pos(200,200)).interpret(gpanel);
        GraphicObject go1= gpanel.getGraphicObjectAt(new Point(100,100));
        GraphicObject go2= gpanel.getGraphicObjectAt(new Point(200,200));
        LinkedList<Integer> idL = new LinkedList<>(); idL.add(go1.getID()); idL.add(go2.getID());
        LinkedList<GraphicObject> glist = new LinkedList<>();
        glist.add(go1); glist.add(go2);
        new Group(idL).interpret(gpanel);
        int count=gpanel.getObjectCount();
        int id=new ID(false).getGroupID(glist);

        textField.setText("del " + id);
        StringReader sr = new StringReader(textField.getText());
        CommandParser cmd = new CommandParser(sr);
        caretaker.executeCommand(cmd.getCommand());
        assertEquals(count-glist.size(),gpanel.getObjectCount());
        assertEquals(gpanel.getObjectCount(),new ID(false).getAllObject().size());
        assertEquals(0,new ID(false).getGroup(id).size());
    }
}
