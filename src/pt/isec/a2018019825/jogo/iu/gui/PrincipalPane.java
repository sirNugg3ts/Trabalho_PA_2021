package pt.isec.a2018019825.jogo.iu.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.a2018019825.jogo.iu.gui.estados.AguardaJogador;
import pt.isec.a2018019825.jogo.iu.gui.estados.Replay;
import pt.isec.a2018019825.jogo.logica.JogoObservavel;
import pt.isec.a2018019825.jogo.logica.Situacao;

import static pt.isec.a2018019825.jogo.iu.gui.ConstantesGUI.*;

public class PrincipalPane extends BorderPane {
    private final JogoObservavel jogoObservavel;

    private Connect4Grid tabuleiro;
    Label nomeJogadorAtual;
    Label Creditos;
    Label pecasDouradas;
    Label ronda;
    AguardaJogador botoesAguardaJogador;
    StackPane bottom;
    Replay replay;


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
        Creditos.setText(jogoObservavel.getActualPlayerCreditos());
        pecasDouradas.setText(jogoObservavel.getPecasDouradas());
        ronda.setText(String.valueOf(jogoObservavel.getRondas()));
        tabuleiro.atualiza();

    }

    private void criarVista() {
        setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID,null,new BorderWidths(2))));

        tabuleiro = new Connect4Grid(jogoObservavel);

        bottom = new StackPane();
        if (jogoObservavel.getSituacao() == Situacao.REPLAY){
            replay = new Replay(jogoObservavel);
            bottom.getChildren().addAll(replay);
            setBottom(replay);
        }else{
            botoesAguardaJogador = new AguardaJogador(jogoObservavel);
            bottom.getChildren().add(botoesAguardaJogador);
            bottom.setAlignment(Pos.CENTER);
            setBottom(bottom);
        }


        //Info do jogo
        HBox topBar = new HBox();
        nomeJogadorAtual = new Label("PlayerName");
        Creditos = new Label("5");
        pecasDouradas = new Label("0");
        ronda = new Label("");
        topBar.getChildren().addAll(nomeJogadorAtual,Creditos,pecasDouradas,ronda);
        topBar.setSpacing(5);
        topBar.setPadding(new Insets(5,5,5,5));
        VBox centerGame = new VBox(topBar,tabuleiro);
        setCenter(centerGame);
        centerGame.setAlignment(Pos.CENTER);

    }


}

