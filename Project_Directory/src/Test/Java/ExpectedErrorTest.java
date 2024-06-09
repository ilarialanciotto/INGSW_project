package Test.Java;

import GraphicObject.GraphicObjectPanel;
import Interpreter.CommandParser;
import Interpreter.Create;
import Interpreter.Pos;
import Interpreter.TypeConstruct;
import Memento.Caretaker;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import Exception.MyException;
import javax.swing.*;
import java.io.StringReader;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExpectedErrorTest {

    private static GraphicObjectPanel gpanel= new GraphicObjectPanel();
    private Caretaker caretaker;
    private JTextField textField;
    private JTextArea textArea;

    @BeforeEach
    public void setUp() {
        caretaker = new Caretaker(gpanel,new JTextArea());
        textField = new JTextField();
        textArea = new JTextArea();
    }

    @Test
    @Order(1)
    public void SetUpCreate(){
        new Create(
                new TypeConstruct("circle", 10.0f,null),
                new Pos(100.0f,100.0f)).interpret(gpanel, textArea);
    }

    @DisplayName("Should not be operation when id object or group doesn't exist ")
    @ParameterizedTest
    @ValueSource(strings = {"del id7", "ls id8", "area id8" , "grp id9 , id11",
                          "ungrp g9" , "perimeter id9" ,
                          "scale id8 0.5" , "mv id11 (320.5,140.6)" , "mvoff id7 (20.0,20.0)"})
    public void ExceptionObjectNull(String input) {
        textField.setText(input);
        StringReader sr = new StringReader(textField.getText());
        Assertions.assertThrows(MyException.class, () -> {
            CommandParser cmd = new CommandParser(sr);
            caretaker.executeCommand(cmd.getCommand());
        });
    }

    @DisplayName("Should not be operation when unexpected command ")
    @ParameterizedTest
    @ValueSource(strings = {"create rectangle(50.0,50.0) (140.5,120.0)"  ,
                            "new circle (10.0) (-144.0,166.5)" ,
                              "mv id0 (320,140)", "mvoff id0 (-10.0,-10.0)" ,
                           "new rectangle (-50.0,40.0) (200.0,166.0)" , "scale id0 -2.0"})
    public void ExceptionUnexpectedCommand(String input) {
        textField.setText(input);
        StringReader sr = new StringReader(textField.getText());
        Assertions.assertThrows(MyException.class, () -> {
            CommandParser cmd = new CommandParser(sr);
            caretaker.executeCommand(cmd.getCommand());
        });
    }

    @DisplayName("Should not be operation when parameters are malformed")
    @ParameterizedTest
    @ValueSource(strings = {"new circle (0.0) (144.0,166.5)",
                            "new img (pippo.jpg) (100.0,200.0)",
                            "new rectangle (0.0,0.0) (200.0,166.0)", " scale id0 0.0"})
    public void ExceptionCommandMalformed(String input) {
        textField.setText(input);
        StringReader sr = new StringReader(textField.getText());
        CommandParser cmd = new CommandParser(sr);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            caretaker.executeCommand(cmd.getCommand());
        });
    }
}
