package pt.isec.a2018019825.jogo.logica.memento;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;

public class CareTaker implements Serializable {
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

    public void undo() throws Exception{
        if (stackHistorico.isEmpty())
            return;
            Memento anterior = stackHistorico.pop();
            originator.setMemento(anterior);

    }


}
