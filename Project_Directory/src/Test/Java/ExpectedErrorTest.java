package Test.Java;

import GraphicView.GraphicObjectPanel;
import Interpreter.CommandParser;
import Memento.Caretaker;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import Exception.MyException;
import javax.swing.*;
import java.io.StringReader;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

@Order(2)
public class ExpectedErrorTest {

    private static GraphicObjectPanel gpanel= new GraphicObjectPanel();
    private Caretaker caretaker;
    private JTextField textField;
    private JTextArea textArea;

    @BeforeEach
    public void setUp() {
        caretaker = new Caretaker(gpanel);
        textField = new JTextField();
        textArea = new JTextArea();
    }

    @DisplayName("Should not be operation when id object or group doesn't exist ")
    @ParameterizedTest
    @ValueSource(strings = {"del id7", "ls id8", "area id8" , "ungrp g9" , "perimeter id9" ,
            "scale id8 0.5" , "mv id11 (320.5,140.6)" ,"mvoff id7 (20.0,20.0)"})
    public void ExceptionObjectNull(String input) {
        textField.setText(input);
        StringReader sr = new StringReader(textField.getText());
        CommandParser cmd = new CommandParser(sr);
        Assertions.assertThrows(MyException.class, () -> {
            caretaker.executeCommand(cmd.getCommand());
        });
    }

    @DisplayName("Should not be operation when unexpected command ")
    @ParameterizedTest
    @ValueSource(strings = {"create rectangle(50.0,50.0) (140.5,120.0)"  ,
                            "new circle (10.0) (-144.0,166.5)",
                           "mv id3 (320,140)", "mv id3 (-10.0,-10.0)" ,
                           "new rectangle (-50.0,40.0) (200.0,166.0)" ,
                            "grp id9 , id11" , "scale id3 -2.0"})
    public void ExceptionUnexpectedCommand(String input) {
        textField.setText(input);
        StringReader sr = new StringReader(textField.getText());
        Assertions.assertThrows(MyException.class, () -> {
            CommandParser cmd = new CommandParser(sr);;
        });
    }

    @DisplayName("Should not be operation when parameters are malformed")
    @ParameterizedTest
    @ValueSource(strings = {"new circle (0.0) (144.0,166.5)",
                            "new img (pippo.jpg) (100.0,200.0)",
                            "new rectangle (0.0,0.0) (200.0,166.0)", " scale id3 0.0"})
    public void ExceptionCommandMalformed(String input) {
        textField.setText(input);
        StringReader sr = new StringReader(textField.getText());
        CommandParser cmd = new CommandParser(sr);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            caretaker.executeCommand(cmd.getCommand());
        });
    }
}
