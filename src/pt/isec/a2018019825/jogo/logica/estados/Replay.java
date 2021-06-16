package pt.isec.a2018019825.jogo.logica.estados;

import pt.isec.a2018019825.jogo.logica.Situacao;
import pt.isec.a2018019825.jogo.logica.dados.Jogo4EmLinha;

public class Replay extends EstadoAdapter{
    public Replay(Jogo4EmLinha jogo) {
        super(jogo);

    }

    @Override
    public IEstado jogaPeca(int coluna) throws Exception {
        return new Replay(jogo);
    }

    @Override
    public Situacao getSituacaoAtual() {
        return Situacao.REPLAY;
    }
}
