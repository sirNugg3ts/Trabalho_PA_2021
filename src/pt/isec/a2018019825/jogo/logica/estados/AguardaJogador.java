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

        if (jogo.vezJogador1())
            jogo.recorda("Player1: coluna " + coluna);
        else
            jogo.recorda("Player2: coluna " + coluna);

        jogo.colocaPeca(coluna);




        //verificar proximo estado

        if (jogo.verificaVencedor('Y')) { //player 1 ganhou
            jogo.setWinner(true);
            jogo.recorda("Player1: Venceu");
            return new FimJogo(jogo);
        } else if (jogo.verificaVencedor('R')) {//player 2 ganhou
            jogo.setWinner(false);
            jogo.recorda("Player2: Venceu");
            return new FimJogo(jogo);
        }

        if (jogo.tabuleiroCheio()){
            jogo.recorda("Fim: Empate");// ta cheio, e ninguem ganhou, logo empate
            return new FimJogo(jogo);
        }


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
        if (jogo.vezJogador1())
            jogo.recorda("Player1: coluna " + x);
        else
            jogo.recorda("Player2: coluna " + x);
        jogo.colocaPeca(x);


        if (jogo.verificaVencedor('Y')) { //player 1 ganhou
            jogo.setWinner(true);
            jogo.recorda("Player1: Venceu");
            return new FimJogo(jogo);
        } else if (jogo.verificaVencedor('R')) {//player 2 ganhou
            jogo.setWinner(false);
            jogo.recorda("Player2: Venceu");
            return new FimJogo(jogo);
        }
        if (jogo.tabuleiroCheio()) {
            jogo.recorda("Fim: Empate");// ta cheio, e ninguem ganhou, logo empate
            return new FimJogo(jogo);
        }


        //se ninguem ganhou nem esta em empate nem é para ir a minijogo, próxima ronda
        if (((!jogo.isPlayerOneComplete() && jogo.getNRounds() == 8) || (!jogo.isPlayerTwoComplete() && jogo.getNRounds() == 9)) && !jogo.isNextPlayerBot())
            return new AguardaMiniJogo(jogo);

        return new AguardaJogador(jogo);
    }

}
