package pt.isec.a2018019825.jogo.logica.estados;

import pt.isec.a2018019825.jogo.logica.Situacao;
import pt.isec.a2018019825.jogo.logica.dados.Jogo4EmLinha;

public class AguardaJogador extends EstadoAdapter{
    public AguardaJogador(Jogo4EmLinha jogo) {
        super(jogo);
    }

    @Override
    public IEstado jogaPeca(int coluna) {
        if (jogo.colunaCheia(coluna)){
            System.out.println("Coluna Cheia!");
            return this;
        }

        jogo.colocaPeca(coluna);

        //TODO: Verificar estado do tabuleiro
        if (jogo.verificaVencedor('Y')){
            jogo.setWinner(true);
            return new FimJogo(jogo);
        }else if(jogo.verificaVencedor('R')){
            jogo.setWinner(false);
            return new FimJogo(jogo);
        }

        if (jogo.tabuleiroCheio())
            return new FimJogo(jogo);

        return new AguardaJogador(jogo);
    }

    @Override
    public Situacao getSituacaoAtual() {
        if (jogo.vezJogador1())
            return Situacao.AGUARDA_JOGADOR1;
        else
            return Situacao.AGUARDA_JOGADOR2;
    }

}
