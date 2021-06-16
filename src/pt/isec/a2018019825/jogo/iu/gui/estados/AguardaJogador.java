package pt.isec.a2018019825.jogo.iu.gui.estados;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.*;
import pt.isec.a2018019825.jogo.iu.gui.ConstantesGUI;
import pt.isec.a2018019825.jogo.iu.gui.VoltarAtrasPane;
import pt.isec.a2018019825.jogo.logica.JogoObservavel;

import java.util.ArrayList;
import java.util.List;

public class AguardaJogador extends HBox {

    boolean mode;        //false -> peca dourada
                         //true -> peca normal

    JogoObservavel jogoObservavel;

    ButtonBar botoesNormais;

    Button JogarPecaNormal, JogarPecaDourada, VoltarAtras;
    ButtonsColunas buttoesColunas;
    Button next;

    VBox voltarAtrasPane;

    public AguardaJogador(JogoObservavel jogoObservavel) {
        this.jogoObservavel = jogoObservavel;
        criaComponentes();
        registaListeners();
        registaObservers();
    }

    private void criaComponentes() {
        getChildren().clear();

        if (jogoObservavel.isNextPlayerBot()){
            next = new Button("Next");
            botoesNormais = new ButtonBar();
            botoesNormais.getButtons().add(next);
        }
        else{
            JogarPecaNormal = new Button("Jogar Peça Normal");
            JogarPecaDourada = new Button("Jogar Peça Dourada");
            VoltarAtras = new Button("Voltar atrás");

            botoesNormais = new ButtonBar();
            botoesNormais.getButtons().addAll(JogarPecaNormal,JogarPecaDourada,VoltarAtras);
            JogarPecaDourada.setDisable(jogoObservavel.getIntPecasDouradas() == 0);
        }

        getChildren().addAll(botoesNormais);

        botoesNormais.setVisible(true);
        setAlignment(Pos.CENTER);


    }

    private void atualiza() {
        getChildren().clear();
        criaComponentes();
        registaListeners();
        registaObservers();
    }

    private void botTime() {
        if (jogoObservavel.isNextPlayerBot()) {
            try {
                jogoObservavel.playBot();
            } catch (Exception e) {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText("Erro!");
                alerta.setContentText(e.getMessage());
                alerta.showAndWait();
            }
        }
    }

    private void registaObservers() {
        jogoObservavel.addPropertyChangeListener(ConstantesGUI.CANCELA_JOGADA, evt -> {
            atualiza();
        });

        jogoObservavel.addPropertyChangeListener(ConstantesGUI.PROPRIEDADE_PLAYPIECE, evt -> {
            atualiza();
        });

        jogoObservavel.addPropertyChangeListener(ConstantesGUI.PROPRIEDADE_ENDMINIGAME, evt -> atualiza());

        jogoObservavel.addPropertyChangeListener(ConstantesGUI.RESPONDEMINIGAME, evt -> {
            if ((int) evt.getNewValue() == -1) {
                atualiza();
            }
        });

        jogoObservavel.addPropertyChangeListener(ConstantesGUI.CANCELA_VOLTARATRAS, evt -> {
            atualiza();
        });

        jogoObservavel.addPropertyChangeListener(ConstantesGUI.VOLTARATRAS, evt -> {
            atualiza();
        });

        jogoObservavel.addPropertyChangeListener(ConstantesGUI.PROPRIEDADE_CARREGAJOGO, evt -> {
            atualiza();
        });
    }

    private void registaListeners() {
        if (JogarPecaNormal != null){
            JogarPecaNormal.setOnAction((e) -> {
                buttoesColunas = new ButtonsColunas(jogoObservavel);
                getChildren().removeAll(botoesNormais);
                getChildren().add(buttoesColunas);
                buttoesColunas.setVisible(true);
                mode = true;
            });
        }

        if (JogarPecaDourada != null){
            JogarPecaDourada.setOnAction((e) -> {
                buttoesColunas = new ButtonsColunas(jogoObservavel);
                getChildren().removeAll(botoesNormais);
                getChildren().add(buttoesColunas);
                buttoesColunas.setVisible(true);
                mode = false;
            });
        }

        if (VoltarAtras != null){

            VoltarAtras.setOnAction(actionEvent -> {
                voltarAtrasPane = new VoltarAtrasPane(jogoObservavel);
                getChildren().removeAll(botoesNormais);
                getChildren().add(voltarAtrasPane);
                voltarAtrasPane.setVisible(true);

            });
        }

        if (next != null){
            next.setOnAction(actionEvent -> botTime());
        }
    }


    class ButtonsColunas extends ButtonBar {
        JogoObservavel jogoObservavel;
        List<Button> butoesColunas;
        Button cancelar;

        public ButtonsColunas(JogoObservavel jogoObservavel) {
            this.jogoObservavel = jogoObservavel;
            criaVista();
            registaListeners();
            atualizar();
        }

        private void atualizar() {
            for (int i = 0; i < 7; i++) {
                butoesColunas.get(i).setDisable(jogoObservavel.colunaCheia(i));
            }
        }

        private void registaListeners() {
            for (Button b : butoesColunas) {
                b.setOnAction(actionEvent -> {
                    if (mode) {
                        try {
                            jogoObservavel.jogaPeca(butoesColunas.indexOf(b));
                        } catch (Exception e) {
                            Alert alerta = new Alert(Alert.AlertType.ERROR);
                            alerta.setHeaderText("Erro!");
                            alerta.setContentText(e.getMessage());
                            alerta.showAndWait();
                        }
                    } else {
                        try {
                            jogoObservavel.jogaPecaDourada(butoesColunas.indexOf(b));
                        } catch (Exception e) {
                            Alert alerta = new Alert(Alert.AlertType.ERROR);
                            alerta.setHeaderText("Erro!");
                            alerta.setContentText(e.getMessage());
                            alerta.showAndWait();
                        }
                    }
                });
            }
            cancelar.setOnAction(e -> {
                jogoObservavel.cancelaJogada();
            });
        }

        private void criaVista() {
            getChildren().clear();
            butoesColunas = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                butoesColunas.add(new Button(Integer.toString(i+1)));
                this.getButtons().addAll(butoesColunas.get(i));
            }
            cancelar = new Button("Cancelar");
            getButtons().addAll(cancelar);
        }
    }
}


