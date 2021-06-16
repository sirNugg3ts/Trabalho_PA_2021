package pt.isec.a2018019825.jogo.iu.gui.estados;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.a2018019825.jogo.iu.gui.ConstantesGUI;
import pt.isec.a2018019825.jogo.logica.JogoObservavel;

public class AguardaMiniJogo extends VBox {
    JogoObservavel jogoObservavel;

    Label playerName;
    HBox box;
    Button yes,no;
    ButtonBar buttons;

    public AguardaMiniJogo(JogoObservavel jogoObservavel) {
        this.jogoObservavel = jogoObservavel;
        criaVista();
        registaListeners();
        registaObservers();
    }

    private void registaObservers() {
        jogoObservavel.addPropertyChangeListener(ConstantesGUI.PROPRIEDADE_PLAYPIECE,evt -> atualizar());
        jogoObservavel.addPropertyChangeListener(ConstantesGUI.PROPRIEDADE_ENDMINIGAME,evt -> atualizar());
    }

    private void atualizar() {
      getChildren().clear();
      criaVista();
      registaListeners();
      registaObservers();
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
            playerName = new Label(jogoObservavel.getPlayerOneName() + ", pode jogar um minijogo para ganhar uma peça dourada Jogar?");
        }else
            playerName = new Label(jogoObservavel.getPlayerTwoName() + ", pode jogar um minijogo para ganhar uma peça dourada Jogar?");
        playerName.setId("questao_minijogo");

        buttons = new ButtonBar();
        yes = new Button("Sim");
        no = new Button("Não");
        buttons.getButtons().addAll(yes,no);

        buttons.setPadding(new Insets(5));

        box= new HBox(buttons);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(5));

        getChildren().addAll(playerName,box);

        setAlignment(Pos.CENTER);
    }
}
