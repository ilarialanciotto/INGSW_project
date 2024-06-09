package Test.Java;

import GraphicObject.GraphicObject;
import GraphicView.GraphicObjectPanel;
import Interpreter.CommandParser;
import Interpreter.List;
import Memento.Caretaker;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import GraphicObject.ID;
import javax.swing.*;
import java.awt.geom.Point2D;
import java.io.StringReader;
import static org.junit.Assert.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class List_Perimeter_AreaCommandTest {

    private static GraphicObjectPanel gpanel= new GraphicObjectPanel();
    private Caretaker caretaker;
    private JTextField textField;
    private JTextArea textArea;
    private GraphicObject [] glist=new GraphicObject [6];
    private int count,countGroup;

    @BeforeEach
    public void setUp() {
        caretaker = new Caretaker(gpanel);
        textField = new JTextField();
        textArea = new JTextArea();
    }

    @DisplayName("Create object setUp")
    @ParameterizedTest
    @ValueSource(strings = {"new rectangle (50.0,30.0) (100.0,100.0)",
            "new circle (20.0) (200.0,200.0)",
            "new img (C:\\Users\\ilari\\OneDrive\\Desktop\\img1.jpg) (300.0,300.0)",
            "new circle (10.0) (150.0,150.0)", "new rectangle (50.0,50.0) (250.0,250.0)",
            "new rectangle (70.0,40.0) (350.0,350.5)" , "grp id0,id1" , "grp id2,id3" , "grp id4,id5" ,
             "grp id6,id7,id8"})
    @Order(1)
    public void SetUpCreateObject(String input) {
        textField.setText(input);
        StringReader sr = new StringReader(textField.getText());
        CommandParser cmd = new CommandParser(sr);
        caretaker.executeCommand(cmd.getCommand());
    }

    @DisplayName("List command test")
    @ParameterizedTest
    @ValueSource(strings = {"ls rectangle", "ls all",  "ls groups" })
    @Order(2)
    public void ListCommandTest(String input) {

        glist[0]=gpanel.getGraphicObjectAt(new Point2D.Double(100.0, 100.0));
        glist[1]=gpanel.getGraphicObjectAt(new Point2D.Double(200.0, 200.0));
        glist[2]=gpanel.getGraphicObjectAt(new Point2D.Double(300.0, 300.0));
        glist[3]=gpanel.getGraphicObjectAt(new Point2D.Double(150.0, 150.0));
        glist[4]=gpanel.getGraphicObjectAt(new Point2D.Double(250.0, 250.0));
        glist[5]=gpanel.getGraphicObjectAt(new Point2D.Double(350.0, 350.5));

        textField.setText(input);
        StringReader sr = new StringReader(textField.getText());
        CommandParser cmd = new CommandParser(sr);
        caretaker.executeCommand(cmd.getCommand());
        String info= "";

        if(input.equals("ls rectangle"))
            info=glist[0].toString() + "\n" + glist[4].toString() + "\n" +  glist[5].toString() + "\n";
        else if(input.equals("ls all"))
            for(int i=0;i<6;i++) info += (glist[i].toString() + "\n") ;
        else if(input.equals("ls groups"))
            info= "Group id 6\n" + glist[0].toString() + "\n" + glist[1].toString() + "\n" +
                    "Group id 7\n" + glist[2].toString() + "\n" + glist[3].toString() + "\n" +
                    "Group id 8\n" + glist[4].toString() + "\n" + glist[5].toString() + "\n" +
                    "Group id 9\n" + glist[0].toString() + "\n" + glist[1].toString() + "\n" +
                    glist[2].toString() + "\n" + glist[3].toString() + "\n" +
                    glist[4].toString() + "\n" + glist[5].toString() + "\n";
        assertEquals(info, new List().getInfoShow());
    }

    @DisplayName("Perimeter command test")
    @ParameterizedTest
    @ValueSource(strings = {"perimeter circle", "perimeter all"})
    @Order(3)
    public void PerimeterCommandTest(String input) {

        glist[0]=gpanel.getGraphicObjectAt(new Point2D.Double(100.0, 100.0));
        glist[1]=gpanel.getGraphicObjectAt(new Point2D.Double(200.0, 200.0));
        glist[2]=gpanel.getGraphicObjectAt(new Point2D.Double(300.0, 300.0));
        glist[3]=gpanel.getGraphicObjectAt(new Point2D.Double(150.0, 150.0));
        glist[4]=gpanel.getGraphicObjectAt(new Point2D.Double(250.0, 250.0));
        glist[5]=gpanel.getGraphicObjectAt(new Point2D.Double(350.0, 350.5));

        textField.setText(input);
        StringReader sr = new StringReader(textField.getText());
        CommandParser cmd = new CommandParser(sr);
        caretaker.executeCommand(cmd.getCommand());
        float perimeterExpected=0;
        float perimeter=0;

        if(input.equals("perimeter circle")){
            for(GraphicObject g: new ID(false).getAllObject().values())
                if(g.getType().equalsIgnoreCase("circle"))
                    perimeterExpected+=g.Perimeter();
            perimeter= (float) ((2*Math.PI*20) + (2*Math.PI*10));
        }
        else if(input.equals("perimeter all")){
            for(GraphicObject g: new ID(false).getAllObject().values())
                perimeterExpected+=g.Perimeter();
            perimeter= (float) ((2*Math.PI*20) + (2*Math.PI*10) + (50+30)*2 + (50+50)*2 +
                    (70+40)*2 + (glist[2].getDimension().getWidth()+glist[2].getDimension().getHeight())*2);
        }
        assertEquals(perimeterExpected,perimeter,0);
    }

    @DisplayName("Area command test")
    @ParameterizedTest
    @ValueSource(strings = {"area img", "area all"})
    @Order(4)
    public void AreaCommandTest(String input) {

        glist[0]=gpanel.getGraphicObjectAt(new Point2D.Double(100.0, 100.0));
        glist[1]=gpanel.getGraphicObjectAt(new Point2D.Double(200.0, 200.0));
        glist[2]=gpanel.getGraphicObjectAt(new Point2D.Double(300.0, 300.0));
        glist[3]=gpanel.getGraphicObjectAt(new Point2D.Double(150.0, 150.0));
        glist[4]=gpanel.getGraphicObjectAt(new Point2D.Double(250.0, 250.0));
        glist[5]=gpanel.getGraphicObjectAt(new Point2D.Double(350.0, 350.5));

        textField.setText(input);
        StringReader sr = new StringReader(textField.getText());
        CommandParser cmd = new CommandParser(sr);
        caretaker.executeCommand(cmd.getCommand());
        float areaExpected=0;
        float area=0;

        if(input.equals("area img")){
            for(GraphicObject g: new ID(false).getAllObject().values())
                if(g.getType().equalsIgnoreCase("img"))
                    areaExpected+=g.Area();
            area= (float) (glist[2].getDimension().getWidth()*glist[2].getDimension().getHeight());
        }
        else if(input.equals("area all")){
            for(GraphicObject g: new ID(false).getAllObject().values())
                areaExpected+=g.Area();
            area= (float) ((Math.PI*Math.pow(20,2)) + (Math.PI*Math.pow(10,2)) + (50*30) + (50*50)+
                    (70*40) + (glist[2].getDimension().getWidth()*glist[2].getDimension().getHeight()));
        }
        assertEquals(areaExpected,area,0);
    }

}
