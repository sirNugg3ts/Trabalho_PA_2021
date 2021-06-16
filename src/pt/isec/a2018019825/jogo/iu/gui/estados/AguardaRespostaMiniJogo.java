package pt.isec.a2018019825.jogo.iu.gui.estados;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import pt.isec.a2018019825.jogo.iu.gui.ConstantesGUI;
import pt.isec.a2018019825.jogo.logica.JogoObservavel;
import pt.isec.a2018019825.jogo.logica.Situacao;

public class AguardaRespostaMiniJogo extends BorderPane {

    JogoObservavel jogoObservavel;

    VBox gameBox;

    Label questao,NCorretas;
    TextField answer;

    Pane paneJogo;

    public AguardaRespostaMiniJogo(JogoObservavel jogoObservavel) {
        this.jogoObservavel = jogoObservavel;
        criaVista();
        registaObserver();
    }

    private void registaObserver() {
        jogoObservavel.addPropertyChangeListener(ConstantesGUI.PROPRIEDADE_CHOOSEMINIGAME, evt -> {
            if ((int)evt.getNewValue() == 1){
                startGame();
            }
        });
    }

    private void startGame() {
        jogoObservavel.startMiniGame();
        if (jogoObservavel.getSituacao() == Situacao.MIINIJOGO_MATHGAME)
            paneJogo = new MathGame();
        else
            paneJogo = new TypeRacer();
    }

    private void criaVista() {
        questao = new Label();
        answer = new TextField();
        gameBox = new VBox(questao,answer);
        gameBox.setAlignment(Pos.CENTER);
        NCorretas = new Label();
        setTop(NCorretas);
        setCenter(gameBox);
        questao.setId("minijogo");
        answer.setPadding(new Insets(5));
    }




    class TypeRacer extends VBox{
        public TypeRacer() {
            questao.setText(jogoObservavel.obtemTypeRacerQuestao());
            registaListeners();
        }

        private void registaListeners() {
            answer.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ENTER){
                    jogoObservavel.validaQuestaoTypeRacer(answer.getText());
                }
            });
        }

    }

    class MathGame extends VBox{
        public MathGame() {
            questao.setText(jogoObservavel.obtemMathQuestao());
            registaListeners();
            registaObserver();
        }

        private void registaObserver() {
            jogoObservavel.addPropertyChangeListener(ConstantesGUI.RESPONDEMINIGAME,evt -> atualiza());

        }

        private void atualiza(){
            questao.setText(jogoObservavel.obtemMathQuestao());
            NCorretas.setText((jogoObservavel.getAcertadas()));
        }

        private void registaListeners() {
            answer.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ENTER){
                    if (answer.getText().matches("-?[0-9]+")){
                        jogoObservavel.validaQuestaoMath(Integer.parseInt(answer.getText()));
                        answer.setText("");
                    }
                    else
                        answer.setText("");
                }
            });
        }
    }

}
