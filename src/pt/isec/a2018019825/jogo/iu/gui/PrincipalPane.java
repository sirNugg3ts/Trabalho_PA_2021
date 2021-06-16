package pt.isec.a2018019825.jogo.iu.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import pt.isec.a2018019825.jogo.iu.gui.estados.AguardaJogador;
import pt.isec.a2018019825.jogo.iu.gui.estados.Replay;
import pt.isec.a2018019825.jogo.logica.JogoObservavel;
import pt.isec.a2018019825.jogo.logica.Situacao;

import static pt.isec.a2018019825.jogo.iu.gui.ConstantesGUI.*;

public class PrincipalPane extends VBox {
    private final JogoObservavel jogoObservavel;

    private Connect4Grid tabuleiro;
    Label nomeJogadorAtual;
    Label Creditos;
    Label pecasDouradas;
    AguardaJogador botoesAguardaJogador;
    StackPane bottom;
    Replay replay;


    public PrincipalPane(JogoObservavel jogoObservavel){
        this.jogoObservavel = jogoObservavel;

        criarVista();
        registaObservador();
        atualiza();

    }

    private void registaObservador() {
        jogoObservavel.addPropertyChangeListener(PROPRIEDADE_JOGO,(evt -> atualiza()));
        jogoObservavel.addPropertyChangeListener(PROPRIEDADE_INGAME,evt -> atualiza());
        jogoObservavel.addPropertyChangeListener(PROPRIEDADE_PLAYPIECE,evt -> atualiza());
        jogoObservavel.addPropertyChangeListener(PROPRIEDADE_ENDMINIGAME,evt -> atualiza());
        jogoObservavel.addPropertyChangeListener(VOLTARATRAS,evt -> atualiza());
        jogoObservavel.addPropertyChangeListener(REPLAY,evt -> atualiza());
    }

    private void atualiza(){
        //atualizar nome para quem vai jogar
        if (jogoObservavel.vezJogador1()){
            nomeJogadorAtual.setText(jogoObservavel.getPlayerOneName());

        }else{
            nomeJogadorAtual.setText(jogoObservavel.getPlayerTwoName());
        }
        Creditos.setText("Créditos: " + jogoObservavel.getActualPlayerCreditos());
        pecasDouradas.setText("Peças Douradas: "+jogoObservavel.getPecasDouradas());
        tabuleiro.atualiza();

    }

    private void criarVista() {
        tabuleiro = new Connect4Grid(jogoObservavel);
        HBox paneTabuleiro = new HBox(tabuleiro);

        paneTabuleiro.setId("tabuleiro");



        bottom = new StackPane();

        if (jogoObservavel.getSituacao() == Situacao.REPLAY){
            replay = new Replay(jogoObservavel);
            bottom.getChildren().addAll(replay);
        }else{
            botoesAguardaJogador = new AguardaJogador(jogoObservavel);
            bottom.getChildren().add(botoesAguardaJogador);
        }


        //Info do jogo
        HBox topBar = new HBox();
        nomeJogadorAtual = new Label("PlayerName");
        Creditos = new Label("Créditos: 5");
        pecasDouradas = new Label("Peças Douradas: 0");
        Creditos.setId("BigLabel");
        pecasDouradas.setId("BigLabel");

        nomeJogadorAtual.setId("BigLabel");
        topBar.getChildren().addAll(nomeJogadorAtual,Creditos,pecasDouradas);
        topBar.setId("TopBarInfoJogo");
        topBar.setAlignment(Pos.CENTER);



        VBox centerGame = new VBox(topBar,paneTabuleiro);
        HBox box = new HBox(centerGame);
        box.setAlignment(Pos.CENTER);

        getChildren().addAll(topBar,box,bottom);


        topBar.setId("barra_estado_jogo");

    }


}

