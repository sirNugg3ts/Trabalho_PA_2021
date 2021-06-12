package pt.isec.a2018019825.jogo.logica.estados;

import pt.isec.a2018019825.jogo.logica.Situacao;
import pt.isec.a2018019825.jogo.logica.dados.Jogo4EmLinha;

public class AguardaMiniJogo extends EstadoAdapter {

    public AguardaMiniJogo(Jogo4EmLinha jogo) {
        super(jogo);
    }

    @Override
    public IEstado minijogo() {
        return new Minijogo(jogo);
    }


    @Override
    public IEstado ignoraMiniJogo() {
        jogo.completeMiniGame(jogo.vezJogador1());
        return new AguardaJogador(jogo);
    }

    @Override
    public Situacao getSituacaoAtual() {
        return Situacao.AGUARDA_MINIJOGO;
    }
}
