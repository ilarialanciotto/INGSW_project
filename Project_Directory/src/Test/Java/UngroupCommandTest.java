package Test.Java;

import GraphicObject.GraphicObject;
import GraphicView.GraphicObjectPanel;
import Interpreter.*;
import Memento.Caretaker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import GraphicObject.ID;
import javax.swing.*;
import java.awt.*;
import java.io.StringReader;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

public class UngroupCommandTest {

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
    @DisplayName("Ungroup command test")
    public void TestRemoveGroupCommand(){
        new Create( new TypeConstruct("circle",20,null),new Pos(130,120)).interpret(gpanel);
        new Create( new TypeConstruct("circle",30,null),new Pos(205,210)).interpret(gpanel);
        GraphicObject go1= gpanel.getGraphicObjectAt(new Point(130,120));
        GraphicObject go2= gpanel.getGraphicObjectAt(new Point(205,210));
        LinkedList<Integer> idL = new LinkedList<>(); idL.add(go1.getID()); idL.add(go2.getID());
        LinkedList<GraphicObject> glist = new LinkedList<>();
        glist.add(go1); glist.add(go2);
        new Group(idL).interpret(gpanel);
        int count=gpanel.getObjectCount();
        int Groupcount=new ID(false).getAllGroup().size();
        int id=new ID(false).getGroupID(glist);

        textField.setText("ungrp " + id);
        StringReader sr = new StringReader(textField.getText());
        CommandParser cmd = new CommandParser(sr);
        caretaker.executeCommand(cmd.getCommand());
        assertEquals(count,gpanel.getObjectCount());
        assertEquals(count,new ID(false).getAllObject().size());
        assertEquals(Groupcount-1,new ID(false).getAllGroup().size());
    }

}
