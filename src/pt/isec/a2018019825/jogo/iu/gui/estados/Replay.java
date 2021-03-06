package pt.isec.a2018019825.jogo.iu.gui.estados;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import pt.isec.a2018019825.jogo.logica.JogoObservavel;

public class Replay extends HBox {
    JogoObservavel jogoObservavel;

    Button next;

    public Replay(JogoObservavel jogoObservavel) {
        this.jogoObservavel = jogoObservavel;
        criaVista();
        registaListeners();
    }

    private void registaListeners() {
        next.setOnAction(actionEvent -> {
            try {
                jogoObservavel.replay();
            } catch (Exception e) {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText("Erro!");
                alerta.setContentText(e.getMessage());
                alerta.showAndWait();
            }
        });
    }

    private void criaVista() {
        next = new Button("Next");
        getChildren().addAll(next);
        setAlignment(Pos.CENTER);
    }
}
