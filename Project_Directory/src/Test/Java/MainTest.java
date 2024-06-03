package Test.Java;

import static org.junit.Assert.*;

import GraphicView.GraphicObjectPanel;
import Interpreter.CommandParser;
import Memento.Caretaker;
import org.junit.*;

import javax.swing.*;
import java.io.StringReader;

public class MainTest {

    private Caretaker caretaker;
    private GraphicObjectPanel gpanel;
    private JTextField textField;
    private JTextArea textArea;

    @Before
    public void setUp() {
        gpanel = new GraphicObjectPanel();
        caretaker = new Caretaker(gpanel);
        textField = new JTextField();
        textArea = new JTextArea();
    }

    @Test
    public void testUndoRedo() {
        String commandText = "new rectangle (50,50) (200,200)";
        textField.setText(commandText);
        StringReader sr = new StringReader(textField.getText());
        CommandParser cmd = new CommandParser(sr);
        caretaker.executeCommand(cmd.getCommand());
        int count=gpanel.getObjectCount();

        caretaker.undo();
        assertEquals(count-1, gpanel.getObjectCount());

        caretaker.redo();
        assertEquals(count, gpanel.getObjectCount());
    }

    @Test
    public void testInvalidCommand() {
        String invalidCommandText = "new rectangle (50,50 (200 200)";
        textField.setText(invalidCommandText);
        StringReader sr = new StringReader(textField.getText());
        try {
            CommandParser cmd = new CommandParser(sr);
            caretaker.executeCommand(cmd.getCommand());
            fail("Expected RuntimeException");
        } catch (RuntimeException e) { }
    }

    @Test
    public void testCommandExecution() {
        String commandText = "new rectangle (50,50) (200,200)";
        textField.setText(commandText);
        StringReader sr = new StringReader(textField.getText());
        CommandParser cmd = new CommandParser(sr);
        int count=gpanel.getObjectCount();
        caretaker.executeCommand(cmd.getCommand());

        assertEquals(count+1, gpanel.getObjectCount());
    }

}
