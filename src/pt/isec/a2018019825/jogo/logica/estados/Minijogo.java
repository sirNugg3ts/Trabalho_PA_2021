package pt.isec.a2018019825.jogo.logica.estados;

import pt.isec.a2018019825.jogo.logica.Situacao;
import pt.isec.a2018019825.jogo.logica.dados.Jogo4EmLinha;

public class Minijogo extends EstadoAdapter{

    public Minijogo(Jogo4EmLinha jogo) {
        super(jogo);
    }


    @Override
    public IEstado acabaMiniJogo() {
        jogo.completeMiniGame(jogo.vezJogador1());
        if ((!jogo.isPlayerOneComplete() && jogo.getNRounds() == 6) || (!jogo.isPlayerTwoComplete() && jogo.getNRounds() == 7))
            return new AguardaMiniJogo(jogo);
        return new AguardaJogador(jogo);
    }

    @Override
    public Situacao getSituacaoAtual() {
        return Situacao.MINIJOGO;
    }
}

