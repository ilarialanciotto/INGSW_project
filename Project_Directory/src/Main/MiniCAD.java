package Main;

import GraphicObject.CircleObject;
import GraphicObject.ImageObject;
import GraphicObject.RectangleObject;
import GraphicView.*;
import Interpreter.CommandParser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.StringReader;

public class MiniCAD {
    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setTitle("MiniCAD");
        frame.setPreferredSize(new Dimension(500,600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final GraphicObjectPanel gpanel=new GraphicObjectPanel();
        gpanel.setPreferredSize(new Dimension(500, 400));
        frame.add(gpanel,BorderLayout.NORTH);

        GraphicObjectViewFactory.FACTORY.installView(RectangleObject.class, new RectangleObjectView());
        GraphicObjectViewFactory.FACTORY.installView(CircleObject.class, new CircleObjectView());
        GraphicObjectViewFactory.FACTORY.installView(ImageObject.class, new ImageObjectView());

        JTextArea textArea=new JTextArea();
        textArea.setPreferredSize(new Dimension(500,100));
        textArea.setEnabled(false);
        textArea.setDisabledTextColor(new Color(0xFF000000, true));
        frame.add(textArea, BorderLayout.CENTER);

        JTextField textField = new JTextField();
        frame.add(textField, BorderLayout.SOUTH);

        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.insert(textField.getText() + "\n", 0);
                StringReader sr = new StringReader(textField.getText());
                try {
                    CommandParser cmd= new CommandParser(sr);
                    cmd.getCommand().interpret(gpanel);
                    textField.setText("");
                }catch(RuntimeException error) {
                    JOptionPane.showMessageDialog(gpanel, error.getMessage());
                    textArea.setText("");
                    textField.setText("");
                }
            }
        });

        frame.pack();
        frame.setVisible(true);
    }
}
