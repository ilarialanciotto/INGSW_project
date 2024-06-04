package Test.Java;

import static org.junit.Assert.*;

import GraphicObject.ID;
import GraphicView.GraphicObjectPanel;
import Interpreter.*;
import Memento.Caretaker;
import org.junit.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runners.Parameterized;
import javax.swing.*;
import java.io.StringReader;
import java.util.Collection;
import java.util.LinkedList;

public class MainTest {

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
        textField.setText("del 0");
        StringReader sr = new StringReader(textField.getText());
        CommandParser cmd = new CommandParser(sr);
        int count=gpanel.getObjectCount();
        caretaker.executeCommand(cmd.getCommand());
        assertEquals(count-1,gpanel.getObjectCount());
    }

    @Test
    @DisplayName("Group command test")
    public void TestGroupCommand(){}

    @Test
    @DisplayName("Ungroup command test")
    public void TestUngroupCommand(){}

    @Test
    @DisplayName("Move command test")
    public void TestMoveCommand(){}

    @Test
    @DisplayName("Scale command test")
    public void TestScaleCommand(){}

    @Nested
    class ParameterizedTests {

        @DisplayName("Create command test")
        @ParameterizedTest
        @ValueSource(strings = {"new rectangle (50,30) (100,100)",
                                 "new circle (20) (100,100)",
                                  "new img (C:\\Users\\ilari\\OneDrive\\Desktop\\img1.jpg) (100,100)"})
        public void TestCreateCommand(String input){
            textField.setText(input);
            StringReader sr = new StringReader(textField.getText());
            CommandParser cmd = new CommandParser(sr);
            int count=gpanel.getObjectCount();
            caretaker.executeCommand(cmd.getCommand());
            assertEquals(count+1,gpanel.getObjectCount());
            assertEquals(gpanel.getObjectCount(),new ID(false).getAllObject().size());
        }

        @DisplayName("Perimeter command test")
        public void TestPerimeterCommand(){}

        @DisplayName("Area command test")
        public void TestAreaCommand(){}

        @DisplayName("List command test")
        public void TestListCommand(){}

    }



}
