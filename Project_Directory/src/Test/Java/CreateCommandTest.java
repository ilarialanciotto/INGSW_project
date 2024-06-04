package Test.Java;

import GraphicObject.ID;
import GraphicView.GraphicObjectPanel;
import Interpreter.*;
import Memento.Caretaker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import javax.swing.*;
import java.io.StringReader;
import static org.junit.Assert.assertEquals;


public class CreateCommandTest {

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

    @DisplayName("Create command test")
    @ParameterizedTest
    @ValueSource(strings = {"new rectangle (50,30) (100,100)",
                "new circle (20) (100,100)",
                "new img (C:\\Users\\ilari\\OneDrive\\Desktop\\img1.jpg) (100,100)"})
    public void TestCreateCommand(String input) {
        textField.setText(input);
        StringReader sr = new StringReader(textField.getText());
        CommandParser cmd = new CommandParser(sr);
        int count = gpanel.getObjectCount();
        caretaker.executeCommand(cmd.getCommand());
        assertEquals(count + 1, gpanel.getObjectCount());
        assertEquals(gpanel.getObjectCount(), new ID(false).getAllObject().size());
    }
}
