package pt.isec.a2018019825.jogo.iu.gui.estados;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import pt.isec.a2018019825.jogo.iu.gui.Connect4Grid;
import pt.isec.a2018019825.jogo.logica.JogoObservavel;

import java.io.IOException;

public class Replay extends BorderPane {
    JogoObservavel jogo;

    Button next;

    public Replay(JogoObservavel jogo) {
        this.jogo = jogo;
        criaVista();
        registaListeners();
    }

    private void registaListeners() {
    }

    private void criaVista() {
        next = new Button("Próximo");
    }
}
