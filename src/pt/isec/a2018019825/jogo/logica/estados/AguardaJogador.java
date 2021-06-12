package pt.isec.a2018019825.jogo.logica.estados;

import pt.isec.a2018019825.jogo.logica.Situacao;
import pt.isec.a2018019825.jogo.logica.dados.Jogo4EmLinha;

import java.util.Random;

public class AguardaJogador extends EstadoAdapter {
    IEstado next = null;

    public AguardaJogador(Jogo4EmLinha jogo) {
        super(jogo);
    }

    @Override
    public IEstado jogaPecaDourada(int coluna) {
        jogo.limpaColuna(coluna);
        if ((!jogo.isPlayerOneBot() && !jogo.isPlayerOneComplete() && jogo.getPlayerOneRounds() == 3) || ((!jogo.isPlayerTwoBot()) && !jogo.isPlayerTwoComplete() && jogo.getPlayerTwoRounds() == 3))
            return new AguardaMiniJogo(jogo);

        return new AguardaJogador(jogo);
    }

    @Override
    public IEstado jogaPeca(int coluna) throws Exception {

        savePlayToLog(coluna);
        jogo.colocaPeca(coluna);


        //verificar proximo estado
        next = checkNextStage();

        if (next != null)
            return next;


        if (!jogo.vezJogador1()){
            if (!jogo.isPlayerTwoBot() && !jogo.isPlayerTwoComplete() && jogo.getPlayerTwoRounds() == 3)
                return new AguardaMiniJogo(jogo);
        }else{
            if (!jogo.isPlayerOneBot() && !jogo.isPlayerOneComplete() && jogo.getPlayerOneRounds() == 3)
                return new AguardaMiniJogo(jogo);
        }
        //se ninguem ganhou nem esta em empate nem é para ir a minijogo, próxima ronda
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
    public IEstado playBot() throws Exception {
        Random random = new Random();
        int x;

        do {
            x = random.nextInt(6);
        } while (jogo.colunaCheia(x));

        savePlayToLog(x);

        jogo.colocaPeca(x);

        //verificar proximo estado
        next = checkNextStage();

        if (next != null)
            return next;

        if ((!jogo.isPlayerOneBot() && !jogo.isPlayerOneComplete() && jogo.getPlayerOneRounds() == 3) || ((!jogo.isPlayerTwoBot()) && !jogo.isPlayerTwoComplete() && jogo.getPlayerTwoRounds() == 3))
            return new AguardaMiniJogo(jogo);

        return new AguardaJogador(jogo);
    }

    private void savePlayToLog(int x) {
        if (jogo.vezJogador1())
            jogo.recorda(jogo.getNomeJogador1() + ": coluna " + x);
        else
            jogo.recorda(jogo.getNomeJogador2() + ": coluna " + x);
    }

    private IEstado checkNextStage() throws Exception {

        if (jogo.verificaVencedor('Y')) { //player 1 ganhou
            jogo.setWinner(true);
            jogo.recorda(jogo.getNomeJogador1() + ": Venceu");
            jogo.sealLog();

            return new FimJogo(jogo);
        } else if (jogo.verificaVencedor('R')) {//player 2 ganhou
            jogo.setWinner(false);
            jogo.recorda(jogo.getNomeJogador2() + ": Venceu");
            jogo.sealLog();
            return new FimJogo(jogo);
        } else if (jogo.tabuleiroCheio()) {
            jogo.recorda("Fim: Empate");// ta cheio, e ninguem ganhou, logo empate
            jogo.sealLog();
            return new FimJogo(jogo);
        }
        return null;
    }

}
