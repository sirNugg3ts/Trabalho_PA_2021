package pt.isec.a2018019825.jogo.iu.gui;


import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.a2018019825.jogo.iu.gui.estados.AguardaJogador;
import pt.isec.a2018019825.jogo.logica.JogoObservavel;
import pt.isec.a2018019825.jogo.logica.Situacao;

import static pt.isec.a2018019825.jogo.iu.gui.ConstantesGUI.*;

public class PrincipalPane extends BorderPane {
    private JogoObservavel jogoObservavel;

    private Connect4Grid tabuleiro;
    Label nomeJogadorAtual;
    Label Creditos;
    AguardaJogador botoesAguardaJogador;


    public PrincipalPane(JogoObservavel jogoObservavel){
        this.jogoObservavel = jogoObservavel;

        criarVista();
        registaObservador();
        registaListeners();
        atualiza();
    }

    private void registaListeners() {


    }

    private void registaObservador() {
        jogoObservavel.addPropertyChangeListener(PROPRIEDADE_JOGO,(evt -> atualiza()));
        jogoObservavel.addPropertyChangeListener(PROPRIEDADE_INGAME,evt -> atualiza());
        jogoObservavel.addPropertyChangeListener(PROPRIEDADE_PLAYPIECE,evt -> atualiza());
    }

    private void atualiza(){
        //atualizar nome para quem vai jogar
        if (jogoObservavel.vezJogador1()){
            nomeJogadorAtual.setText(jogoObservavel.getPlayerOneName());

        }else{
            nomeJogadorAtual.setText(jogoObservavel.getPlayerTwoName());
        }
        Creditos.setText(jogoObservavel.getActualPlayerCreditos());

    }

    private void criarVista() {
        setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID,null,new BorderWidths(2))));

        tabuleiro = new Connect4Grid(jogoObservavel);
        setCenter(tabuleiro);

        StackPane bottom = new StackPane();
        botoesAguardaJogador = new AguardaJogador(jogoObservavel);
        bottom.getChildren().add(botoesAguardaJogador);
        setBottom(bottom);

        //Info do jogo
        HBox topBar = new HBox();
        nomeJogadorAtual = new Label("PlayerName");
        Creditos = new Label("5");
        topBar.getChildren().addAll(nomeJogadorAtual,Creditos);
        setTop(topBar);
    }


}

