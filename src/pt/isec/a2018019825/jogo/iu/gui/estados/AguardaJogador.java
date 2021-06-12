package pt.isec.a2018019825.jogo.iu.gui.estados;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import pt.isec.a2018019825.jogo.iu.gui.ConstantesGUI;
import pt.isec.a2018019825.jogo.logica.JogoObservavel;

import java.util.ArrayList;
import java.util.List;

public class AguardaJogador extends HBox {

    boolean mode;        //false -> peca dourada
                        //true -> peca normal

    JogoObservavel jogoObservavel;

    StackPane stack;

    HBox botoesNormais;

    Button JogarPecaNormal,JogarPecaDourada,VoltarAtras;
    ButtonsColunas buttoesColunas;


    public AguardaJogador(JogoObservavel jogoObservavel){
        this.jogoObservavel = jogoObservavel;
        botTime();
        criaComponentes();
        registaListeners();
        registaObservers();
    }

    private void botTime() {
        if (jogoObservavel.isNextPlayerBot()){
            try {
                jogoObservavel.playBot();
            } catch (Exception e) {
                //TODO APRESENTAR MENSAGEM DE ERRO

                e.printStackTrace();
            }
        }
    }

    private void registaObservers() {
        jogoObservavel.addPropertyChangeListener(ConstantesGUI.CANCELA_JOGADA,evt -> {
            buttoesColunas.setVisible(false);
            botoesNormais.setVisible(true);
        });

        jogoObservavel.addPropertyChangeListener(ConstantesGUI.PROPRIEDADE_PLAYPIECE,evt -> {
            buttoesColunas.setVisible(false);
            botoesNormais.setVisible(true);
        });
    }

    private void registaListeners() {
        JogarPecaNormal.setOnAction((e) -> {botoesNormais.setVisible(false);buttoesColunas.setVisible(true);mode = true;});
        JogarPecaDourada.setOnAction((e)->{botoesNormais.setVisible(false);buttoesColunas.setVisible(true);mode = false;});
    }

    private void criaComponentes() {
        stack = new StackPane();

        JogarPecaNormal = new Button("Jogar Peça Normal");
        JogarPecaDourada = new Button("Jogar Peça Dourada");
        VoltarAtras = new Button("Voltar atrás");
        buttoesColunas = new ButtonsColunas(jogoObservavel);

        botoesNormais = new HBox(JogarPecaNormal,JogarPecaDourada,VoltarAtras);
        stack.getChildren().addAll(botoesNormais,buttoesColunas);
        botoesNormais.setVisible(true);
        buttoesColunas.setVisible(false);

        getChildren().addAll(stack);
        setAlignment(Pos.CENTER);
    }

    class ButtonsColunas extends HBox{
        JogoObservavel jogoObservavel;
        List<Button> butoesColunas;
        Button cancelar;

        public ButtonsColunas(JogoObservavel jogoObservavel){
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
            for(Button b : butoesColunas){
                b.setOnAction(actionEvent -> {
                    if (mode){
                        try {
                            jogoObservavel.jogaPeca(butoesColunas.indexOf(b));
                        } catch (Exception e) {
                            Alert alerta = new Alert(Alert.AlertType.ERROR);
                            alerta.setHeaderText("Erro!");
                            alerta.setContentText(e.getMessage());
                            alerta.showAndWait();
                        }
                    }else{
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
            butoesColunas = new ArrayList<>();
            for (int i = 0;i < 7;i++){
                butoesColunas.add(new Button(Integer.toString(i)));
                getChildren().add(butoesColunas.get(i));
            }
            cancelar = new Button("Cancelar");
            getChildren().addAll(cancelar);
        }
    }
}


