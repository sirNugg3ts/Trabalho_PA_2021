package pt.isec.a2018019825.jogo.logica.estados;

import pt.isec.a2018019825.jogo.logica.Situacao;
import pt.isec.a2018019825.jogo.logica.dados.Jogo4EmLinha;

import java.util.Random;

public class AguardaMiniJogo extends EstadoAdapter {

    public AguardaMiniJogo(Jogo4EmLinha jogo) {
        super(jogo);
    }

    @Override
    public IEstado minijogo() {

        Random random = new Random();

        boolean win;

        if (jogo.isTypeRacerEnabled()) { //verificar se o jogo funciona
            boolean x = random.nextBoolean(); //escolher um dos dois jogos ao calhas
            if (x)
                win = jogo.startMathGame();
            else
                win = jogo.startTypeRacer();
        } else // se o typeracer nao funcionar, calha sempre o jogo da matematica
            win = jogo.startMathGame();

        if (win)
            jogo.addPecaDourada(jogo.vezJogador1()); //ganha a peça dourada e pode jogar
        else
            jogo.skipsTurn(); //perdeu, logo nao ganha a ronda e passa a vez para o proximo jogador

        jogo.completeMiniGame(jogo.vezJogador1());

        return new AguardaJogador(jogo);

    }

    @Override
    public Situacao getSituacaoAtual() {
        return Situacao.AGUARDA_MINIJOGO;
    }
}
