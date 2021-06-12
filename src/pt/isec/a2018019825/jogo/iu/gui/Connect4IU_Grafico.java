package pt.isec.a2018019825.jogo.iu.gui;


import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import pt.isec.a2018019825.jogo.iu.gui.estados.AguardaInicioPane;
import pt.isec.a2018019825.jogo.iu.gui.estados.AguardaMiniJogo;
import pt.isec.a2018019825.jogo.logica.JogoObservavel;

public class Connect4IU_Grafico extends BorderPane {

    private  JogoObservavel jogoObservavel;
    private AguardaInicioPane aguardaInicioPane;
    private PrincipalPane panePrincipal;
    private AguardaMiniJogo aguardaMiniJogo;
    private StackPane stack;

    public Connect4IU_Grafico(JogoObservavel jogoObservavel){
        this.jogoObservavel = jogoObservavel;
        criaComponentes();
        disporVista();
        registaObservador();
        registaListeners();
    }

    private void registaListeners() {

    }


    private void registaObservador(){
        //mudar do configura para o jogo em si
        jogoObservavel.addPropertyChangeListener(ConstantesGUI.PROPRIEDADE_JOGO, (e) -> {
            aguardaInicioPane.setVisible(false);
            panePrincipal = new PrincipalPane(jogoObservavel);
            aguardaMiniJogo = new AguardaMiniJogo(jogoObservavel);
            stack.getChildren().addAll(panePrincipal,aguardaMiniJogo);
            panePrincipal.setVisible(true);
            aguardaMiniJogo.setVisible(false);
        });

        //verificar o estado
        jogoObservavel.addPropertyChangeListener(ConstantesGUI.PROPRIEDADE_PLAYPIECE, evt -> {
            switch (jogoObservavel.getSituacao()){
                case AGUARDA_JOGADOR1:
                case AGUARDA_JOGADOR2:{
                    if (jogoObservavel.isNextPlayerBot()) {
                        try {
                            jogoObservavel.playBot();
                        } catch (Exception e) {
                            //TODO APRESENTAR MENSAGEM DE ERRO
                            e.printStackTrace();
                        }
                    }
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
                //TODO IR PARA MINIJOGO
            }
        });

    }

    private void atualiza() {
    }


    private void criaComponentes() {
        stack = new StackPane();
        aguardaInicioPane = new AguardaInicioPane(jogoObservavel);
        panePrincipal = new PrincipalPane(jogoObservavel);
        aguardaMiniJogo = new AguardaMiniJogo(jogoObservavel);


        //Log Window TODO
        /*
        Stage stage2 = new Stage();
        LogPane logPane = new LogPane(jogoObservavel);
        Scene sceneLog = new Scene(logPane,300,800);
        stage2.setScene(sceneLog);
        stage2.show();
        */
    }


    private void disporVista() {
        aguardaInicioPane.setVisible(true);
        stack.getChildren().addAll(aguardaInicioPane);
        setCenter(stack);
    }

}

class LogPane extends Pane{
    JogoObservavel jogoObservavel;
    public LogPane(JogoObservavel jogoObservavel){
        this.jogoObservavel = jogoObservavel;
        createLayout();
        registaListeners();

    }

    private void registaListeners(){

    }

    private void createLayout() {
    }
}
