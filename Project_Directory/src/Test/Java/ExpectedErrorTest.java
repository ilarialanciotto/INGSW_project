package Test.Java;

import GraphicView.GraphicObjectPanel;
import Interpreter.CommandParser;
import Memento.Caretaker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import Exception.MyException;
import javax.swing.*;
import java.io.StringReader;

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
    @ValueSource(strings = {"del id6", "ls id6", "area id6" , "ungrp g9" , "perimeter id6" ,
            "scale id6 0.5" , "mv id6 (320.5,140.6)" ,"mvoff id6 (20.0,20.0)"})
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
    @ValueSource(strings = {"create rectangle(50.0,50.0) (140.5,120.0)" , "mv id2 (320,140)" ,
                           "mv id2 (-10.0,-10.0)" , "new circle (10.0) (-144.0,166.5)",
                           "scale id2 -2" , "new rectangle (-50.0,40.0) (200.0,166.0)" ,
                            "grp id6 , id7" })
    public void ExceptionUnexpectedCommand(String input) {
        textField.setText(input);
        StringReader sr = new StringReader(textField.getText());
        Assertions.assertThrows(MyException.class, () -> {
            CommandParser cmd = new CommandParser(sr);;
        });
    }

    @DisplayName("Should not be operation when parameters are malformed")
    @ParameterizedTest
    @ValueSource(strings = {"new circle (0.0) (144.0,166.5)","scale id2 0" ,
                            "new img (pippo.jpg) (100.0,200.0)",
                            "new rectangle (0.0,0.0) (200.0,166.0)" })
    public void ExceptionCommandMalformed(String input) {
        textField.setText(input);
        StringReader sr = new StringReader(textField.getText());
        CommandParser cmd = new CommandParser(sr);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            caretaker.executeCommand(cmd.getCommand());
        });
    }
}
