package pt.isec.a2018019825.jogo.iu.gui;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import pt.isec.a2018019825.jogo.iu.gui.estados.*;
import pt.isec.a2018019825.jogo.iu.gui.resources.CSSManager;
import pt.isec.a2018019825.jogo.logica.JogoObservavel;
import pt.isec.a2018019825.jogo.logica.MaquinaEstados;
import pt.isec.a2018019825.jogo.logica.Situacao;

import java.io.File;
import java.io.IOException;

public class Connect4IU_Grafico extends BorderPane {

    private PrincipalPane panePrincipal;

    private JogoObservavel jogoObservavel;
    private AguardaInicioPane aguardaInicioPane;

    private AguardaMiniJogo aguardaMiniJogo;
    private AguardaRespostaMiniJogo aguardaRespostaMiniJogoPane;
    private FimJogo fimJogoPane;
    private StackPane stack;
    private MenuBar menu;

    MenuItem carrega;
    MenuItem save;

    MenuItem PrideMode;

    public Connect4IU_Grafico(){
        comeca();
    }

    private void comeca(){
        jogoObservavel = new JogoObservavel(new MaquinaEstados());
        CSSManager.setCSS(this,"style.css");
        criaComponentes();
        disporVista();
        registaObservador();
    }

    private void criaComponentes() {
        stack = new StackPane();
        aguardaInicioPane = new AguardaInicioPane(jogoObservavel);

        menu = new MenuBar();

        Menu jogoMenu = new Menu("_File");
        Menu specialMode = new Menu(" ");

        carrega = new MenuItem("Carregar Jogo / Replay");
        save = new MenuItem("Guardar Jogo");
        jogoMenu.getItems().addAll(carrega,save);
        menu.getMenus().addAll(jogoMenu,specialMode);
        carrega.setOnAction(new lerObjSave());
        save.setOnAction(new GuardarObjListener());
        save.setDisable(true);

        PrideMode = new MenuItem("Pride Mode");

        specialMode.getItems().addAll(PrideMode);
        PrideMode.setOnAction(actionEvent -> ConstantesGUI.SpecialMode = !ConstantesGUI.SpecialMode);
    }

    private void disporVista() {
        aguardaInicioPane.setVisible(true);
        menu.setVisible(true);
        stack.getChildren().addAll(aguardaInicioPane);
        setTop(menu);
        setCenter(stack);

        this.setId("PanePrincipal");
    }


    private void registaObservador(){



        jogoObservavel.addPropertyChangeListener(ConstantesGUI.COMECANOVOJOGO,evt -> {
            stack.getChildren().clear();
            comeca();
            save.setDisable(false);
        });

        jogoObservavel.addPropertyChangeListener(ConstantesGUI.REPLAY,evt -> {
            save.setDisable(true);
            stack.getChildren().clear();
            panePrincipal = new PrincipalPane(jogoObservavel);

            stack.getChildren().addAll(panePrincipal);
            panePrincipal.setVisible(true);

            if (jogoObservavel.getSituacao() == Situacao.FIM_JOGO){
                fimJogoPane = new FimJogo(jogoObservavel);
                stack.getChildren().addAll(fimJogoPane);
                panePrincipal.setVisible(false);
                fimJogoPane.setVisible(true);
            }
        });

        jogoObservavel.addPropertyChangeListener(ConstantesGUI.PROPRIEDADE_CARREGAJOGO,evt -> {
            stack.getChildren().clear();
            panePrincipal = new PrincipalPane(jogoObservavel);
            aguardaMiniJogo = new AguardaMiniJogo(jogoObservavel);
            aguardaRespostaMiniJogoPane = new AguardaRespostaMiniJogo(jogoObservavel);
            fimJogoPane = new FimJogo(jogoObservavel);
            stack.getChildren().addAll(panePrincipal,aguardaMiniJogo, aguardaRespostaMiniJogoPane,fimJogoPane);

            save.setDisable(false);


            panePrincipal.setVisible(true);
            aguardaInicioPane.setVisible(false);
            aguardaMiniJogo.setVisible(false);
            aguardaRespostaMiniJogoPane.setVisible(false);
            fimJogoPane.setVisible(false);
        });

        jogoObservavel.addPropertyChangeListener(ConstantesGUI.PROPRIEDADE_JOGO, (e) -> {
            stack.getChildren().clear();
            panePrincipal = new PrincipalPane(jogoObservavel);
            aguardaMiniJogo = new AguardaMiniJogo(jogoObservavel);
            aguardaRespostaMiniJogoPane = new AguardaRespostaMiniJogo(jogoObservavel);
            fimJogoPane = new FimJogo(jogoObservavel);
            stack.getChildren().addAll(panePrincipal,aguardaMiniJogo, aguardaRespostaMiniJogoPane,fimJogoPane);

            panePrincipal.setVisible(true);
            aguardaInicioPane.setVisible(false);
            aguardaMiniJogo.setVisible(false);
            aguardaRespostaMiniJogoPane.setVisible(false);
            fimJogoPane.setVisible(false);
            save.setDisable(false);
        });


        jogoObservavel.addPropertyChangeListener(ConstantesGUI.PROPRIEDADE_PLAYPIECE, evt -> {
            switch (jogoObservavel.getSituacao()){
                case FIM_JOGO:{
                    save.setDisable(true);
                    panePrincipal.setVisible(false);
                    fimJogoPane.setVisible(true);
                    break;
                }

                case AGUARDA_MINIJOGO:{
                    save.setDisable(true);
                    panePrincipal.setVisible(false);
                    aguardaMiniJogo.setVisible(true);
                    break;
                }
                default:
                    save.setDisable(false);
                    break;

            }

        });

        jogoObservavel.addPropertyChangeListener(ConstantesGUI.PROPRIEDADE_CHOOSEMINIGAME,evt -> {
            save.setDisable(true);
            if ((int)evt.getNewValue() == -1){
                aguardaMiniJogo.setVisible(false);
                panePrincipal.setVisible(true);
            }else{
                aguardaMiniJogo.setVisible(false);
                panePrincipal.setVisible(false);
                aguardaRespostaMiniJogoPane.setVisible(true);
            }
        });

        jogoObservavel.addPropertyChangeListener(ConstantesGUI.PROPRIEDADE_ENDMINIGAME, evt -> {
            save.setDisable(false);
            aguardaRespostaMiniJogoPane.setVisible(false);
            panePrincipal.setVisible(true);

            switch (jogoObservavel.getSituacao()){
                case AGUARDA_MINIJOGO:{
                    save.setDisable(true);
                    panePrincipal.setVisible(false);
                    aguardaMiniJogo.setVisible(true);
                    break;
                }}

        });

        jogoObservavel.addPropertyChangeListener(ConstantesGUI.VOLTARATRAS,evt -> {
            save.setDisable(false);
            switch (jogoObservavel.getSituacao()){
                case AGUARDA_MINIJOGO:{
                    save.setDisable(true);
                    panePrincipal.setVisible(false);
                    aguardaMiniJogo.setVisible(true);
                    break;
                }
            }
        });
    }

    private class GuardarObjListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("."));
            File selectedFile = fileChooser.showSaveDialog(null);
            if (selectedFile != null){
                try {
                    jogoObservavel.gravaJogo(selectedFile);
                } catch (IOException e) {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setHeaderText("Erro ao gravar ficheiro!");
                    alerta.setContentText(e.getMessage());
                    alerta.showAndWait();
                }
            }
        }
    }

    private class lerObjSave implements EventHandler<ActionEvent> {
        //SAVE
        @Override
        public void handle(ActionEvent actionEvent) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("."));
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null){
                try {
                    jogoObservavel.lerJogo(selectedFile);
                } catch (Exception e) {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setHeaderText("Erro ao carregar ficheiro!");
                    alerta.setContentText("Ficheiro Inválido / Corrupto");
                    alerta.showAndWait();
                }
            }
        }
    }


}
