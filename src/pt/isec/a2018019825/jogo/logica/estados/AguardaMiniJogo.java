package pt.isec.a2018019825.jogo.logica.estados;

import pt.isec.a2018019825.jogo.logica.Situacao;
import pt.isec.a2018019825.jogo.logica.dados.Jogo4EmLinha;

import java.util.Random;

public class AguardaMiniJogo extends EstadoAdapter{

    public AguardaMiniJogo(Jogo4EmLinha jogo) {
        super(jogo);
    }

    @Override
    public IEstado minijogo() {

        Random random = new Random();

        boolean win;

        if (jogo.isTypeRacerEnabled()){
            boolean x = random.nextBoolean();
            if (x)
                win = jogo.startMathGame();
            else
                win = jogo.startTypeRacer();
        }else
            win = jogo.startMathGame();

        if (win)
            jogo.addPecaDourada(jogo.vezJogador1());
        else
            jogo.skipsTurn();

        return new AguardaJogador(jogo);

    }

    @Override
    public Situacao getSituacaoAtual() {
        return Situacao.AGUARDA_MINIJOGO;
    }
}
