package Test.Java;

import GraphicObject.GraphicObjectPanel;
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

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommandTest {

    private static GraphicObjectPanel gpanel= new GraphicObjectPanel();
    private Caretaker caretaker;
    private JTextField textField;
    private JTextArea textArea;
    private  GraphicObject g,g1;
    private int count,countGroup;
    private Point2D oldPosition,oldPosition1;
    private Dimension2D oldDimention,oldDimention1;

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
            "new img (C:\\Users\\ilari\\OneDrive\\Desktop\\img1.jpg) (300.0,300.0)",
            "new circle (10.0) (150.0,150.0)"})
    @Order(1)
    public void TestCreateCommand(String input) {
        textField.setText(input);
        StringReader sr = new StringReader(textField.getText());
        CommandParser cmd = new CommandParser(sr);
        int count = new ID(false).getAllObject().size();
        caretaker.executeCommand(cmd.getCommand());
        assertEquals(count + 1, new ID(false).getAllObject().size());
    }

    @DisplayName("Command test")
    @ParameterizedTest
    @ValueSource(strings = {"ls id0", "perimeter id0" , "scale id0 2.0" ,
            "area id0" ,"mv id0 (250.0,250.0)" , "mvoff id0 (10.0,10.0)" , "del id0" })
    @Order(2)
    public void TestCommand(String input) {

        if(input.startsWith("mvoff")) {
            g= gpanel.getGraphicObjectAt(new Point2D.Double(250.0, 250.0));
            oldPosition = g.getPosition();
        }
        else {
            g= gpanel.getGraphicObjectAt(new Point2D.Double(100.0, 100.0));
            if (input.startsWith("del")) count=new ID(false).getAllObject().size();
            if(input.startsWith("scale")) oldDimention=g.getDimension();
        }

        textField.setText(input);
        StringReader sr = new StringReader(textField.getText());
        CommandParser cmd = new CommandParser(sr);
        caretaker.executeCommand(cmd.getCommand());

        if(input.startsWith("del")){
            assertEquals(count - 1, new ID(false).getAllObject().size());

        }else if(input.startsWith("ls")){
            String info=g.Information() + "\n";
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
        }
    }

    @DisplayName("CommandGroupTest")
    @ParameterizedTest
    @ValueSource(strings = {"grp id1, id2" , "ls id4", "scale id4 0.5", "mv id4 (250.5,250.5)" ,
                            "mvoff id4 (20.0,20.0)", "del id4" , "ungrp g4"})
    @Order(3)
    public void TestGroupCommand(String input) {

        if(input.startsWith("mvoff")){
            g= new ID(false).getObject(1);
            g1=new ID(false).getObject(2);
            oldPosition = g.getPosition();
            oldPosition1 = g1.getPosition();
        }
        else{
            g=gpanel.getGraphicObjectAt(new Point2D.Float(200.0f,200.0f));
            g1=gpanel.getGraphicObjectAt(new Point2D.Float(300.0f,300.0f));
            if(input.startsWith("scale")){
                oldDimention=g.getDimension();
                oldDimention1=g1.getDimension();
            }
        }

        countGroup=new ID(false).getAllGroup().size();
        count=new ID(false).getAllObject().size();

        textField.setText(input);
        StringReader sr = new StringReader(textField.getText());
        CommandParser cmd = new CommandParser(sr);
        caretaker.executeCommand(cmd.getCommand());

        if(input.startsWith("del")){
            assertEquals(count - 2, new ID(false).getAllObject().size());
            assertEquals(0,new ID(false).getGroup(4).size());

        }else if(input.startsWith("ls")){
            String info="Group id 4\n" +  g.toString() + "\n" + g1.toString() + "\n";
            assertEquals(info,new List().getInfoShow());

        }else if(input.startsWith("perimeter")){
            Dimension2D D=g1.getDimension();
            Float perimeter= (float) ((20.0*2*Math.PI) + (D.getHeight()+D.getWidth())*2);
            assertEquals(perimeter,g.Perimeter(),0);

        }else if(input.startsWith("area")){
            Dimension2D D=g1.getDimension();
            Float area= (float) (Math.PI*Math.pow(20.0,2) + D.getHeight()*D.getWidth());
            assertEquals(area,g.Area(),0);

        }else if(input.startsWith("mv ")){
            Point2D currentPos=g.getPosition();
            Point2D currentPos1=g1.getPosition();
            assertEquals(currentPos,new Point2D.Double(250.5,250.5));
            assertEquals(currentPos1,new Point2D.Double(250.5,250.5));

        }else if(input.startsWith("mvoff")){
            Point2D currentPos=g.getPosition();
            Point2D currentPos1=g1.getPosition();
            assertEquals(currentPos.getX(),oldPosition.getX()+20.0,0);
            assertEquals(currentPos.getY(),oldPosition.getY()+20.0,0);
            assertEquals(currentPos1.getX(),oldPosition1.getX()+20.0,0);
            assertEquals(currentPos1.getY(),oldPosition1.getY()+20.0,0);

        }else if(input.startsWith("scale")){
            Dimension2D currentDimention= g.getDimension();
            Dimension2D currentDimention1= g1.getDimension();
            assertEquals(oldDimention.getWidth()*0.5,currentDimention.getWidth(),0);
            assertEquals(oldDimention.getHeight()*0.5,currentDimention.getHeight(),0);
            assertEquals(oldDimention1.getWidth()*0.5,currentDimention1.getWidth(),0);
            assertEquals(oldDimention1.getHeight()*0.5,currentDimention1.getHeight(),0);

        }else if(input.startsWith("grp")){
            assertEquals(countGroup + 1, new ID(false).getAllGroup().size());
        }
        else if(input.startsWith("ungrp")){
            assertEquals(countGroup - 1, new ID(false).getAllGroup().size());
            assertEquals(count, new ID(false).getAllObject().size());
        }
    }
}
