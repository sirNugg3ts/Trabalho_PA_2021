package pt.isec.a2018019825.jogo.logica.memento;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;

public class CareTaker implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final IMementoOriginator originator;

    private Deque<Memento> stackHistorico = new ArrayDeque<>();
    private Deque<Memento> stackReplay = new ArrayDeque<>();

    public CareTaker(IMementoOriginator originator) {
        this.originator = originator;
    }

    public void gravaMemento() throws IOException {

            stackHistorico.push(originator.getMemento());
            if (stackHistorico.size()>10)
                stackHistorico.removeFirst();

            stackReplay.push(originator.getMemento());
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
