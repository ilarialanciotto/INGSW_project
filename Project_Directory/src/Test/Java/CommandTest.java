package Test.Java;

import GraphicView.GraphicObjectPanel;
import Interpreter.CommandParser;
import Interpreter.List;
import Memento.Caretaker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import GraphicObject.GraphicObject;
import javax.swing.*;
import java.io.StringReader;
import GraphicObject.ID;
import static org.junit.Assert.assertEquals;
import java.awt.geom.Point2D;
import java.awt.geom.Dimension2D;
import java.util.LinkedList;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommandTest {

    private static GraphicObjectPanel gpanel= new GraphicObjectPanel();
    private Caretaker caretaker;
    private JTextField textField;
    private JTextArea textArea;
    private  GraphicObject g;
    private int count,countGroup;
    private Point2D oldPosition;
    private Dimension2D oldDimention;

    @BeforeEach
    public void setUp() {
        caretaker = new Caretaker(gpanel);
        textField = new JTextField();
        textArea = new JTextArea();
    }

    @DisplayName("Create command test")
    @ParameterizedTest
    @ValueSource(strings = {"new rectangle (50.0,30.0) (100.0,100.0)",
            "new circle (20.0) (200.0,200.0)",
            "new img (C:\\Users\\ilari\\OneDrive\\Desktop\\img1.jpg) (300.0,300.0)"})
    @Order(1)
    public void TestCreateCommand(String input) {
        textField.setText(input);
        StringReader sr = new StringReader(textField.getText());
        CommandParser cmd = new CommandParser(sr);
        int count = gpanel.getObjectCount();
        caretaker.executeCommand(cmd.getCommand());
        assertEquals(count + 1, gpanel.getObjectCount());
        assertEquals(gpanel.getObjectCount(), new ID(false).getAllObject().size());
    }

    @DisplayName("Command test")
    @ParameterizedTest
    @ValueSource(strings = {"ls id0", "perimeter id0" , "scale id0 2.0" ,
            "area id0" , "grp id0, id1" , "mv id0 (250.0,250.0)" , "mvoff id0 (10.0,10.0)" , "ungrp g3" ,"del id0" })
    @Order(2)
    public void TestCommand(String input) {

        if(input.startsWith("mvoff")) {
            g= gpanel.getGraphicObjectAt(new Point2D.Double(250.0, 250.0));
            oldPosition = g.getPosition();
        }
        else {
            g= gpanel.getGraphicObjectAt(new Point2D.Double(100.0, 100.0));
            if (input.startsWith("del")) count=gpanel.getObjectCount();
            if(input.startsWith("scale")) oldDimention=g.getDimension();
            if(input.startsWith("grp " ) || input.startsWith("ungrp"))
                countGroup=new ID(false).getAllGroup().size();
        }

        textField.setText(input);
        StringReader sr = new StringReader(textField.getText());
        CommandParser cmd = new CommandParser(sr);
        caretaker.executeCommand(cmd.getCommand());

        if(input.startsWith("del")){
            assertEquals(count - 1, gpanel.getObjectCount());
            assertEquals(gpanel.getObjectCount(), new ID(false).getAllObject().size());

        }else if(input.startsWith("ls")){
            String info=g.Information();
            assertEquals(info,new List().getInfoShow());

        }else if(input.startsWith("perimeter")){
            Float perimeter= (float) (50.0+30.0)*2;
            assertEquals(perimeter,g.Perimeter(),0);

        }else if(input.startsWith("area")){
            Float area= (float) (50.0*30.0);
            assertEquals(area,g.Area(),0);

        }else if(input.startsWith("mv ")){
            Point2D currentPos=g.getPosition();
            assertEquals(currentPos,new Point2D.Double(250.0,250.0));

        }else if(input.startsWith("mvoff")){
            Point2D currentPos=g.getPosition();
            assertEquals(currentPos.getX(),oldPosition.getX()+10.0,0);
            assertEquals(currentPos.getY(),oldPosition.getY()+10.0,0);

        }else if(input.startsWith("scale")){
            Dimension2D currentDimention= g.getDimension();
            assertEquals(oldDimention.getWidth()*2,currentDimention.getWidth(),0);
            assertEquals(oldDimention.getHeight()*2,currentDimention.getHeight(),0);

        }else if(input.startsWith("grp"))
            assertEquals(countGroup + 1, new ID(false).getAllGroup().size());
        else if(input.startsWith("ungrp"))
            assertEquals(countGroup-1,new ID(false).getAllGroup().size());
    }
}
