package pt.isec.a2018019825.jogo.logica.estados;

import pt.isec.a2018019825.jogo.logica.Situacao;
import pt.isec.a2018019825.jogo.logica.dados.Jogo4EmLinha;

public class Minijogo extends EstadoAdapter{


    public Minijogo(Jogo4EmLinha jogo) {
        super(jogo);
    }

    @Override
    public IEstado startMiniGame() {
        jogo.startClock();
        return new AguardaRespostaMiniJogo(jogo);
    }

    @Override
    public Situacao getSituacaoAtual() {
        return Situacao.AGUARDA_INICIO_MINIJOGO;
    }
}

