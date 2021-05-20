package pt.isec.a2018019825.jogo.logica.memento;

import java.io.IOException;

public interface IMementoOriginator {
    Memento getMemento() throws IOException;
    void setMemento(Memento m) throws IOException,ClassNotFoundException;
}
