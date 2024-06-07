package Test.Java;

import GraphicObject.GraphicObject;
import GraphicView.GraphicObjectPanel;
import Interpreter.*;
import Memento.Caretaker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import GraphicObject.ID;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.management.StringValueExp;
import javax.swing.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.io.StringReader;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

public class ScaleCommandTest {

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

    @DisplayName("Scale Command Test")
    @ParameterizedTest
    @ValueSource(strings = {"0.5", "2"})
    public void TestScaleCommand(String input){
        new Create( new TypeConstruct("rectangle",new Pos(50,50)),new Pos(50,100)).interpret(gpanel);
        GraphicObject g=gpanel.getGraphicObjectAt(new Point2D.Double(50,100));
        Dimension2D D=g.getDimension();

        textField.setText("scale " + g.getID() + " " + input);
        StringReader sr = new StringReader(textField.getText());
        CommandParser cmd = new CommandParser(sr);
        caretaker.executeCommand(cmd.getCommand());
        float expected1 = (float)D.getHeight()*Float.valueOf(input);
        float expected2 = (float)D.getWidth()*Float.valueOf(input);
        assertEquals(expected1,g.getDimension().getHeight(),0);
        assertEquals(expected2,g.getDimension().getWidth(),0);
    }

    @DisplayName("Scale Group command test")
    @ParameterizedTest
    @ValueSource(strings = {"2", "0.5",})
    public void TestScaleGroupCommand(String input){
        new Create( new TypeConstruct("circle",20,null),new Pos(100,100)).interpret(gpanel);
        new Create( new TypeConstruct("img",0,"C:\\Users\\ilari\\OneDrive\\Desktop\\img1.jpg"),new Pos(200,200)).interpret(gpanel);
        GraphicObject go1=gpanel.getGraphicObjectAt(new Point2D.Double(100,100));
        GraphicObject go2=gpanel.getGraphicObjectAt(new Point2D.Double(200,200));
        LinkedList<Integer> idL = new LinkedList<>(); idL.add(go1.getID()); idL.add(go2.getID());
        new Group(idL).interpret(gpanel);
        LinkedList<GraphicObject> glist = new LinkedList<>();
        glist.add(go1); glist.add(go2);
        Dimension2D D1=go1.getDimension();
        Dimension2D D2=go2.getDimension();

        textField.setText("scale " + new ID(false).getGroupID(glist) + " " + input);
        StringReader sr = new StringReader(textField.getText());
        CommandParser cmd = new CommandParser(sr);
        caretaker.executeCommand(cmd.getCommand());
        float expected1 = (float)D1.getHeight()*Float.valueOf(input);
        float expected2 = (float)D1.getWidth()*Float.valueOf(input);
        float expected3 = (float)D2.getHeight()*Float.valueOf(input);
        float expected4 = (float)D2.getWidth()*Float.valueOf(input);
        assertEquals(expected1,go1.getDimension().getHeight(),0);
        assertEquals(expected2,go1.getDimension().getWidth(),0);
        assertEquals(expected3,go2.getDimension().getHeight(),0);
        assertEquals(expected4,go2.getDimension().getWidth(),0);
    }

}
