package pt.isec.a2018019825.jogo.logica.memento;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

public class CareTaker {
    private final IMementoOriginator originator;

    private Deque<Memento> stackHistorico = new ArrayDeque<>();

    public CareTaker(IMementoOriginator originator) {
        this.originator = originator;
    }

    public void gravaMemento(){
        try{
            stackHistorico.push(originator.getMemento());
            if (stackHistorico.size()>10)
                stackHistorico.removeFirst();

        } catch (IOException e) {
            System.err.println("grava memento: " + e);
        }
    }

    public void limpaStack(){
        stackHistorico.clear();
    }

    public int size(){
        return stackHistorico.size();
    }

    public void undo(){
        if (stackHistorico.isEmpty())
            return;

        try{
            Memento anterior = stackHistorico.pop();
            originator.setMemento(anterior);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("undo: " + e);
        }
    }


}
