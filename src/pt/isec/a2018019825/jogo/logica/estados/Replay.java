package pt.isec.a2018019825.jogo.logica.estados;

import pt.isec.a2018019825.jogo.logica.Situacao;
import pt.isec.a2018019825.jogo.logica.dados.Jogo4EmLinha;

public class Replay extends EstadoAdapter{
    public Replay(Jogo4EmLinha jogo) {
        super(jogo);
    }

    @Override
    public IEstado replay(){

        if (jogo.verificaVencedor('Y')) { //player 1 ganhou
            return new FimJogo(jogo);
        } else if (jogo.verificaVencedor('R')) {//player 2 ganhou
            return new FimJogo(jogo);
        } else if (jogo.tabuleiroCheio()) {
            return new FimJogo(jogo);
        }
        return new Replay(jogo);
    }

    @Override
    public Situacao getSituacaoAtual() {
        return Situacao.REPLAY;
    }
}
