package Test.Java;

import GraphicObject.ID;
import GraphicObject.GraphicObjectPanel;
import Interpreter.CommandParser;
import Memento.Caretaker;
import org.junit.jupiter.api.*;
import GraphicObject.GraphicObject;
import javax.swing.*;
import java.awt.geom.Dimension2D;
import java.io.StringReader;
import java.awt.geom.Point2D;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UndoRedoTest {

    private static GraphicObjectPanel gpanel= new GraphicObjectPanel();
    private Caretaker caretaker;
    private JTextField textField;
    private JTextArea textArea;
    private GraphicObject g,g1;
    private int count,countGroup,Groupg,Groupg1;
    private Point2D oldPosition,oldPosition1;
    private Dimension2D oldDimention,oldDimention1;

    @BeforeEach
    public void setUp() {
        caretaker = new Caretaker(gpanel);
        textField = new JTextField();
        textArea = new JTextArea();
    }

    @DisplayName("Undo_Redo Create Command")
    @RepeatedTest(2)
    @Order(1)
    public void UndoRedoTestCreateCommand(){
        textField.setText("new rectangle (50.0,50.0) (100.0,100.0)");
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
    @Order(7)
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

    @Test
    @DisplayName("Undo_Redo Move Command")
    @Order(2)
    public void UndoRedoTestMoveCommand(){
        textField.setText("mv id1 (200.0,200.0)");
        StringReader sr = new StringReader(textField.getText());
        CommandParser cmd = new CommandParser(sr);
        GraphicObject g = gpanel.getGraphicObjectAt(new Point2D.Float(100.0f, 100.0f));
        caretaker.executeCommand(cmd.getCommand());

        caretaker.undo();
        assertEquals(new Point2D.Float(100.0f,100.0f),g.getPosition());
        caretaker.redo();
        assertEquals(new Point2D.Float(200.0f,200.0f),g.getPosition());
    }

    @Test
    @DisplayName("Undo_Redo MoveOff Command")
    @Order(3)
    public void UndoRedoTestMoveOffCommand(){
        textField.setText("mvoff id3 (20.0,20.0)");
        StringReader sr = new StringReader(textField.getText());
        CommandParser cmd = new CommandParser(sr);
        GraphicObject g = gpanel.getGraphicObjectAt(new Point2D.Float(100.0f, 100.0f));
        caretaker.executeCommand(cmd.getCommand());

        caretaker.undo();
        assertEquals(new Point2D.Float(100.0f,100.0f),g.getPosition());
        caretaker.redo();
        assertEquals(new Point2D.Float(120.0f,120.0f),g.getPosition());
    }

    @Test
    @DisplayName("Undo_Redo Group Command")
    @Order(4)
    public void UndoRedoTestGroupCommand(){
        textField.setText("grp id1,id3");
        StringReader sr = new StringReader(textField.getText());
        CommandParser cmd = new CommandParser(sr);
        caretaker.executeCommand(cmd.getCommand());
        countGroup=new ID(false).getAllGroup().size();
        count=new ID(false).getAllObject().size();
        g=new ID(false).getObject(1);
        g1=new ID(false).getObject(3);
        Groupg=g.getGroup().size();
        Groupg1=g1.getGroup().size();

        caretaker.undo();
        assertEquals(countGroup-1,new ID(false).getAllGroup().size());
        assertEquals(count,new ID(false).getAllObject().size());
        assertEquals(Groupg-1,g.getGroup().size());
        assertEquals(Groupg1-1,g.getGroup().size());
        caretaker.redo();
        assertEquals(countGroup,new ID(false).getAllGroup().size());
        assertEquals(count,new ID(false).getAllObject().size());
        assertEquals(Groupg,g.getGroup().size());
        assertEquals(Groupg1,g.getGroup().size());
    }

    @Test
    @DisplayName("Undo_Redo Ungroup Command")
    @Order(5)
    public void UndoRedoTestUngroupCommand(){
        textField.setText("ungrp g5");
        StringReader sr = new StringReader(textField.getText());
        CommandParser cmd = new CommandParser(sr);
        caretaker.executeCommand(cmd.getCommand());
        countGroup=new ID(false).getAllGroup().size();
        count=new ID(false).getAllObject().size();
        g=new ID(false).getObject(1);
        g1=new ID(false).getObject(3);
        Groupg=g.getGroup().size();
        Groupg1=g1.getGroup().size();

        caretaker.undo();
        assertEquals(countGroup+1,new ID(false).getAllGroup().size());
        assertEquals(count,new ID(false).getAllObject().size());
        assertEquals(Groupg+1,g.getGroup().size());
        assertEquals(Groupg1+1,g.getGroup().size());
        caretaker.redo();
        assertEquals(countGroup,new ID(false).getAllGroup().size());
        assertEquals(count,new ID(false).getAllObject().size());
        assertEquals(Groupg,g.getGroup().size());
        assertEquals(Groupg1,g.getGroup().size());
    }

    @Test
    @DisplayName("Undo_Redo Scale Command")
    @Order(6)
    public void UndoRedoTestScaleCommand(){
        textField.setText("scale id1 2.0");
        StringReader sr = new StringReader(textField.getText());
        CommandParser cmd = new CommandParser(sr);
        GraphicObject g = gpanel.getGraphicObjectAt(new Point2D.Float(200.0f, 200.0f));
        caretaker.executeCommand(cmd.getCommand());
        oldDimention=g.getDimension();

        caretaker.undo();
        Dimension2D currDimention=g.getDimension();
        assertEquals(currDimention.getHeight(),oldDimention.getHeight()/2);
        assertEquals(currDimention.getWidth(),oldDimention.getWidth()/2);
        caretaker.redo();
        currDimention=g.getDimension();
        assertEquals(currDimention.getHeight(),oldDimention.getHeight());
        assertEquals(currDimention.getWidth(),oldDimention.getWidth());
    }


}
