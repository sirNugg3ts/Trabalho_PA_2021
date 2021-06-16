package pt.isec.a2018019825.jogo.iu.gui;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import pt.isec.a2018019825.jogo.iu.gui.estados.*;
import pt.isec.a2018019825.jogo.logica.JogoObservavel;
import pt.isec.a2018019825.jogo.logica.MaquinaEstados;

import java.io.File;
import java.io.IOException;

public class Connect4IU_Grafico extends BorderPane {

    private JogoObservavel jogoObservavel;
    private AguardaInicioPane aguardaInicioPane;
    private PrincipalPane panePrincipal;
    private AguardaMiniJogo aguardaMiniJogo;
    private Minijogo minijogoPane;
    private FimJogo fimJogoPane;
    private StackPane stack;
    private MenuBar menu;
    private Replay replay;

    public Connect4IU_Grafico(){
        comeca();
    }

    private void comeca(){
        jogoObservavel = new JogoObservavel(new MaquinaEstados());
        criaComponentes();
        disporVista();
        registaObservador();
    }

    private void criaComponentes() {
        stack = new StackPane();
        aguardaInicioPane = new AguardaInicioPane(jogoObservavel);

        menu = new MenuBar();

        Menu jogoMenu = new Menu("_File");

        MenuItem carrega = new MenuItem("Carregar Jogo");
        MenuItem carregaReplay = new MenuItem("Carrega Replay");
        MenuItem save = new MenuItem("Guardar Jogo");
        jogoMenu.getItems().addAll(carrega,carregaReplay,save);
        menu.getMenus().add(jogoMenu);
        carrega.setOnAction(new lerObjSave());
        //carregaReplay.setOnAction(new LerObjListener());
        save.setOnAction(new GuardarObjListener());

    }

    private void disporVista() {
        aguardaInicioPane.setVisible(true);
        menu.setVisible(true);
        stack.getChildren().addAll(aguardaInicioPane);
        setTop(menu);
        setCenter(stack);
    }


    private void registaObservador(){

        jogoObservavel.addPropertyChangeListener(ConstantesGUI.COMECANOVOJOGO,evt -> {
            stack.getChildren().clear();
            comeca();
        });

        jogoObservavel.addPropertyChangeListener(ConstantesGUI.REPLAY,evt -> {
            panePrincipal = new PrincipalPane(jogoObservavel);
            replay = new Replay(jogoObservavel);
            stack.getChildren().addAll(panePrincipal,replay);
            panePrincipal.setVisible(true);
            replay.setVisible(true);

        });

        jogoObservavel.addPropertyChangeListener(ConstantesGUI.PROPRIEDADE_CARREGAJOGO,evt -> {
            stack.getChildren().clear();
            panePrincipal = new PrincipalPane(jogoObservavel);
            aguardaMiniJogo = new AguardaMiniJogo(jogoObservavel);
            minijogoPane = new Minijogo(jogoObservavel);
            fimJogoPane = new FimJogo(jogoObservavel);
            stack.getChildren().addAll(panePrincipal,aguardaMiniJogo,minijogoPane,fimJogoPane);



            panePrincipal.setVisible(true);
            aguardaInicioPane.setVisible(false);
            aguardaMiniJogo.setVisible(false);
            minijogoPane.setVisible(false);
            fimJogoPane.setVisible(false);
        });

        //mudar do configura para o jogo em si
        jogoObservavel.addPropertyChangeListener(ConstantesGUI.PROPRIEDADE_JOGO, (e) -> {

            panePrincipal = new PrincipalPane(jogoObservavel);
            aguardaMiniJogo = new AguardaMiniJogo(jogoObservavel);
            minijogoPane = new Minijogo(jogoObservavel);
            fimJogoPane = new FimJogo(jogoObservavel);
            stack.getChildren().addAll(panePrincipal,aguardaMiniJogo,minijogoPane,fimJogoPane);

            panePrincipal.setVisible(true);
            aguardaInicioPane.setVisible(false);
            aguardaMiniJogo.setVisible(false);
            minijogoPane.setVisible(false);
            fimJogoPane.setVisible(false);
        });

        //verificar o estado
        jogoObservavel.addPropertyChangeListener(ConstantesGUI.PROPRIEDADE_PLAYPIECE, evt -> {
            switch (jogoObservavel.getSituacao()){
                case FIM_JOGO:{
                    panePrincipal.setVisible(false);
                    fimJogoPane.setVisible(true);
                    break;
                }

                case AGUARDA_MINIJOGO:{
                    panePrincipal.setVisible(false);
                    aguardaMiniJogo.setVisible(true);
                    break;
                }

            }
        });

        jogoObservavel.addPropertyChangeListener(ConstantesGUI.PROPRIEDADE_CHOOSEMINIGAME,evt -> {
            if ((int)evt.getNewValue() == -1){
                aguardaMiniJogo.setVisible(false);
                panePrincipal.setVisible(true);
            }else{
                aguardaMiniJogo.setVisible(false);
                panePrincipal.setVisible(false);
                minijogoPane.setVisible(true);
            }
        });

        jogoObservavel.addPropertyChangeListener(ConstantesGUI.PROPRIEDADE_ENDMINIGAME, evt -> {
            minijogoPane.setVisible(false);
            panePrincipal.setVisible(true);

            System.out.println(jogoObservavel.getSituacao());

            switch (jogoObservavel.getSituacao()){
                case AGUARDA_MINIJOGO:{
                    panePrincipal.setVisible(false);
                    aguardaMiniJogo.setVisible(true);
                    break;
                }}

        });

        jogoObservavel.addPropertyChangeListener(ConstantesGUI.VOLTARATRAS,evt -> {
            switch (jogoObservavel.getSituacao()){
                case AGUARDA_MINIJOGO:{
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
