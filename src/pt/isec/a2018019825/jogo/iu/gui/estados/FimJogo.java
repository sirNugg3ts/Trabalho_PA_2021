package pt.isec.a2018019825.jogo.iu.gui.estados;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.a2018019825.jogo.iu.gui.ConstantesGUI;
import pt.isec.a2018019825.jogo.logica.JogoObservavel;

public class FimJogo extends VBox {
    JogoObservavel jogoObservavel;
    Label info;
    Button novoJogo,sair;

    public FimJogo(JogoObservavel jogoObservavel) {
        this.jogoObservavel = jogoObservavel;
        criaVista();
        registaListeners();
        registaObservers();
        atualiza();
    }

    private void registaObservers() {
        jogoObservavel.addPropertyChangeListener(ConstantesGUI.PROPRIEDADE_PLAYPIECE,evt -> atualiza());
    }

    private void atualiza() {
        if (jogoObservavel.isTabuleiroCheio())
            info.setText("O jogo terminou: Empate");
        else
            info.setText("O jogo terminou: " + jogoObservavel.getWinner() + " é o vencedor!");
    }


    private void criaVista() {
        info = new Label();
        info.setId("BigLabel");
        novoJogo = new Button("Novo Jogo");
        sair = new Button("Sair");

        HBox butoes = new HBox(novoJogo,sair);
        butoes.setSpacing(10);
        getChildren().addAll(info,butoes);
        info.setAlignment(Pos.CENTER);
        butoes.setAlignment(Pos.CENTER);
        this.setAlignment(Pos.CENTER);
    }

    private void registaListeners() {
        sair.setOnAction(actionEvent -> Platform.exit());

        novoJogo.setOnAction(actionEvent -> {
            jogoObservavel.newGame();
        });
    }
}
