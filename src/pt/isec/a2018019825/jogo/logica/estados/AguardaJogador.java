package pt.isec.a2018019825.jogo.logica.estados;

import pt.isec.a2018019825.jogo.logica.Situacao;
import pt.isec.a2018019825.jogo.logica.dados.Jogo4EmLinha;

import java.util.Random;

public class AguardaJogador extends EstadoAdapter {
    public AguardaJogador(Jogo4EmLinha jogo) {
        super(jogo);
    }

    @Override
    public IEstado jogaPecaDourada(int coluna) {
        jogo.limpaColuna(coluna);
        return new AguardaJogador(jogo);
    }

    @Override
    public IEstado jogaPeca(int coluna) {

        if (jogo.colunaCheia(coluna)) {
            System.out.println("Coluna Cheia!");
            return this;
        }

        jogo.colocaPeca(coluna);

        //verificar proximo estado

        if (jogo.verificaVencedor('Y')) { //player 1 ganhou
            jogo.setWinner(true);
            return new FimJogo(jogo);
        } else if (jogo.verificaVencedor('R')) {//player 2 ganhou
            jogo.setWinner(false);
            return new FimJogo(jogo);
        }

        if (jogo.tabuleiroCheio()) // ta cheio, e ninguem ganhou, logo empate
            return new FimJogo(jogo);

        //se ninguem ganhou nem esta em empate nem é para ir a minijogo, próxima ronda
        if (((!jogo.isPlayerOneComplete() && jogo.getNRounds() == 8) || (!jogo.isPlayerTwoComplete() && jogo.getNRounds() == 9)) && !jogo.isNextPlayerBot())
            return new AguardaMiniJogo(jogo);

        return new AguardaJogador(jogo);
    }

    @Override
    public Situacao getSituacaoAtual() {
        if (jogo.vezJogador1())
            return Situacao.AGUARDA_JOGADOR1;
        else
            return Situacao.AGUARDA_JOGADOR2;
    }

    @Override
    public IEstado playBot() {
        Random random = new Random();
        int x;

        do {
            x = random.nextInt(6);
        } while (jogo.colunaCheia(x));
        jogo.colocaPeca(x);

        if (jogo.verificaVencedor('Y')) { //player 1 ganhou
            jogo.setWinner(true);
            return new FimJogo(jogo);
        } else if (jogo.verificaVencedor('R')) {//player 2 ganhou
            jogo.setWinner(false);
            return new FimJogo(jogo);
        }
        if (jogo.tabuleiroCheio()) // ta cheio, e ninguem ganhou, logo empate
            return new FimJogo(jogo);

        //se ninguem ganhou nem esta em empate nem é para ir a minijogo, próxima ronda
        if (((!jogo.isPlayerOneComplete() && jogo.getNRounds() == 8) || (!jogo.isPlayerTwoComplete() && jogo.getNRounds() == 9)) && !jogo.isNextPlayerBot())
            return new AguardaMiniJogo(jogo);

        return new AguardaJogador(jogo);
    }

}
