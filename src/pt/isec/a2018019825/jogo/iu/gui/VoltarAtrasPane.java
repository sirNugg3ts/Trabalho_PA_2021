package pt.isec.a2018019825.jogo.iu.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.a2018019825.jogo.logica.JogoObservavel;


public class VoltarAtrasPane extends VBox {
    JogoObservavel jogoObservavel;

    Label text;
    Label rondasDisponiveis;
    TextField input;
    Button submit,cancelar;

    public VoltarAtrasPane(JogoObservavel jogoObservavel) {
        this.jogoObservavel = jogoObservavel;
        criaVista();
        registaListeners();
        registaObservers();
    }

    private void registaObservers() {
        jogoObservavel.addPropertyChangeListener(ConstantesGUI.PROPRIEDADE_PLAYPIECE,evt -> atualiza());
        jogoObservavel.addPropertyChangeListener(ConstantesGUI.PROPRIEDADE_ENDMINIGAME,evt -> atualiza());
    }

    private void atualiza() {
        rondasDisponiveis.setText("Rondas disponiveis: " + jogoObservavel.getRondasBackDisponiveis());
        input.setStyle(null);
    }

    private void criaVista() {
        text = new Label("Quantas rondas deseja voltar atrás?");
        text.setId("info_voltar_atras");
        rondasDisponiveis = new Label("Rondas disponiveis: " + jogoObservavel.getRondasBackDisponiveis());
        rondasDisponiveis.setId("info_voltar_atras");
        input = new TextField("");
        submit = new Button("OK");
        cancelar = new Button("Cancelar");
        HBox inputbox = new HBox(submit,cancelar);
        inputbox.setSpacing(5);
        inputbox.setPadding(new Insets(5));
        getChildren().addAll(text,rondasDisponiveis,input,inputbox);

    }

    private void registaListeners() {
        cancelar.setOnAction(actionEvent -> jogoObservavel.cancelaVoltarAtras());
        submit.setOnAction(actionEvent -> {
            if (input.getText().matches("[0-9]+")){
                if (Integer.parseInt(input.getText()) > Integer.parseInt(jogoObservavel.getRondasBackDisponiveis()) || Integer.parseInt(input.getText()) > jogoObservavel.getCreditos() ){
                    input.setText("");
                }else{
                    try {
                        jogoObservavel.voltarAtras(Integer.parseInt(input.getText()));
                        input.setText("");
                    } catch (Exception e) {

                        Alert alerta = new Alert(Alert.AlertType.ERROR);
                        alerta.setHeaderText("Erro ao voltar atrás!");
                        alerta.setContentText(e.getMessage());
                        alerta.showAndWait();
                    }

                }
            }else{
                input.setText("");
            }
        });
    }


}
