package Main;

import GraphicObject.GraphicObjectPanel;
import Interpreter.CommandParser;
import Memento.Caretaker;
import javax.swing.*;
import Exception.MyException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.StringReader;

public class MiniCAD {

    public static void main(String[] args) {

        JFrame frame = new JFrame() ,MiniWindow = new JFrame();;
        frame.setTitle("MiniCAD");
        frame.setPreferredSize(new Dimension(500,600));
        frame.setLocation(10, 10);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setMaximizedBounds(frame.getBounds());

        MiniWindow.setTitle("Windows");
        MiniWindow.setPreferredSize(new Dimension(300,300));
        MiniWindow.setLocation(600, 10);
        MiniWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MiniWindow.setResizable(false);
        MiniWindow.setMaximizedBounds(frame.getBounds());

        final GraphicObjectPanel gpanel=new GraphicObjectPanel();
        gpanel.setPreferredSize(new Dimension(500, 400));

        JTextArea textArea=new JTextArea();
        textArea.setEnabled(false);
        textArea.setDisabledTextColor(new Color(0xFF000000, true));
        frame.add(textArea, BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        frame.getContentPane().add(scrollPane);

        JTextArea textAreaW = new JTextArea();
        textAreaW.setEditable(false);
        MiniWindow.add(textAreaW);
        JScrollPane scrollPane1 = new JScrollPane(textAreaW);
        scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        MiniWindow.getContentPane().add(scrollPane1);

        Caretaker caretaker=new Caretaker(gpanel,textAreaW);
        JPanel panel=new JPanel();
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);
        JButton undoButt = new JButton("Undo");
        JButton redoButt = new JButton("Redo");

        JTextField textField = new JTextField();
        frame.add(textField, BorderLayout.SOUTH);

        toolbar.add(undoButt);
        toolbar.add(redoButt);

        panel.add(toolbar,BorderLayout.NORTH);
        panel.add(gpanel,BorderLayout.CENTER);

        frame.add(panel, BorderLayout.NORTH);

        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.insert(textField.getText() + "\n", 0);
                StringReader sr = new StringReader(textField.getText());
                try {
                    CommandParser cmd= new CommandParser(sr);
                    caretaker.executeCommand(cmd.getCommand());
                    textField.setText("");
                }catch(RuntimeException error) {
                    JOptionPane.showMessageDialog(gpanel, error.getMessage());
                    textArea.setText("");
                    textField.setText("");
                }
            }
        });

        undoButt.addActionListener(evt -> {
            try{
                caretaker.undo();
            }catch(MyException e ){
                JOptionPane.showMessageDialog(gpanel,e.getMessage());
            }
        });

        redoButt.addActionListener(evt -> {
            try{
                caretaker.redo();
            }catch(MyException e ){
                JOptionPane.showMessageDialog(gpanel,e.getMessage());
            }
        });

        MiniWindow.pack();
        MiniWindow.setVisible(true);

        frame.pack();
        frame.setVisible(true);
    }
}