package Test.Java;

import static org.junit.Assert.*;

import GraphicObject.ID;
import GraphicView.GraphicObjectPanel;
import Interpreter.*;
import Memento.Caretaker;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import javax.swing.*;
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
        new Create( new TypeConstruct("circle",20,null),new Pos(100,100)).interpret(gpanel);
        textField.setText("del 3");
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
        new Create( new TypeConstruct("circle",30,null),new Pos(200,300)).interpret(gpanel);
        LinkedList<Integer> ids = new LinkedList<>(); ids.add(0); ids.add(1);
        new Group(ids);

        textField.setText("del 2");
        StringReader sr = new StringReader(textField.getText());
        CommandParser cmd = new CommandParser(sr);
        int count=gpanel.getObjectCount();
        caretaker.executeCommand(cmd.getCommand());
        assertEquals(count-2,gpanel.getObjectCount());
        assertEquals(gpanel.getObjectCount(),new ID(false).getAllObject().size());
        assertEquals(0,new ID(false).getGroup(2).size());
    }
}
