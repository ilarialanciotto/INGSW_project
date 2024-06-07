package Memento;

import GraphicView.GraphicObjectPanel;
import Interpreter.Cmd;
import Exception.MyException;

import javax.swing.*;
import java.util.Stack;

public class Caretaker {

    private Stack<Memento> undoStack = new Stack<>();
    private Stack<Memento> redoStack = new Stack<>();
    private Stack<Cmd> commandStack = new Stack<>();
    private Stack<Cmd> redoCommandStack = new Stack<>();
    private GraphicObjectPanel gpanel;

    public Caretaker(GraphicObjectPanel gpanel) {
        this.gpanel = gpanel;
    }

    public void executeCommand(Cmd command) {
       Memento memento = gpanel.saveToMemento();
       command.interpret(gpanel);
       undoStack.push(memento);
       commandStack.push(command);
       redoStack.clear();
       redoCommandStack.clear();
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            Memento memento = undoStack.pop();
            Cmd command = commandStack.pop();
            redoStack.push(gpanel.saveToMemento());
            redoCommandStack.push(command);
            gpanel.restoreFromMemento(memento);
            command.undo();
        } else { throw new MyException("cannot undo "); }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Memento memento = redoStack.pop();
            Cmd command = redoCommandStack.pop();
            undoStack.push(gpanel.saveToMemento());
            commandStack.push(command);
            gpanel.restoreFromMemento(memento);
            command.interpret(gpanel);
        }else { throw new MyException("cannot redo "); }
    }
}
