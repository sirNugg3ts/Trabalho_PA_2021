package pt.isec.a2018019825.jogo.iu.gui.estados;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.a2018019825.jogo.iu.gui.ConstantesGUI;
import pt.isec.a2018019825.jogo.logica.JogoObservavel;

public class AguardaMiniJogo extends VBox {
    JogoObservavel jogoObservavel;
    HBox buttoes;

    Label playerName;
    Button yes,no;

    public AguardaMiniJogo(JogoObservavel jogoObservavel) {
        this.jogoObservavel = jogoObservavel;
        criaVista();
        registaListeners();
        registaObservers();
        atualizar();
    }

    private void registaObservers() {
        jogoObservavel.addPropertyChangeListener(ConstantesGUI.PROPRIEDADE_PLAYPIECE,evt -> atualizar());
        jogoObservavel.addPropertyChangeListener(ConstantesGUI.PROPRIEDADE_ENDMINIGAME,evt -> atualizar());
    }

    private void atualizar() {
        if (jogoObservavel.vezJogador1()){
            playerName.setText(jogoObservavel.getPlayerOneName() + ", pode jogar um minijogo para ganhar uma peça dourada\n Jogar?");
        }else
            playerName.setText(jogoObservavel.getPlayerTwoName() + ", pode jogar um minijogo para ganhar uma peça dourada\n Jogar?");
    }

    private void registaListeners() {
        yes.setOnAction(actionEvent ->  {
            jogoObservavel.jogaMiniJogo();

        });
        no.setOnAction(actionEvent -> {
            jogoObservavel.ignoraMiniJogo();
        });
    }

    private void criaVista() {
        if (jogoObservavel.vezJogador1()){
            playerName = new Label(jogoObservavel.getPlayerOneName() + ", pode jogar um minijogo para ganhar uma peça dourada\n Jogar?");
        }else
            playerName = new Label(jogoObservavel.getPlayerTwoName() + ", pode jogar um minijogo para ganhar uma peça dourada\n Jogar?");

        playerName.setAlignment(Pos.CENTER);
        yes = new Button("Sim");
        no = new Button("No");
        buttoes = new HBox(yes,no);
        buttoes.setAlignment(Pos.CENTER);

        getChildren().addAll(playerName,buttoes);
        setAlignment(Pos.CENTER);
    }
}
