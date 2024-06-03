package Memento;


import Interpreter.Cmd;

public class Memento {

    private final Cmd state;

    public Memento(Cmd state) {
        this.state = state;
    }

    public Cmd getState() {
        return state;
    }

    public String toString() {
        return "memento state: " + state;
    }
}

