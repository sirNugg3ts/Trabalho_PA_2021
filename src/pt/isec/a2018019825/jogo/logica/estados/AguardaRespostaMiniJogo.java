package pt.isec.a2018019825.jogo.logica.estados;

import pt.isec.a2018019825.jogo.logica.Situacao;
import pt.isec.a2018019825.jogo.logica.dados.Jogo4EmLinha;

public class AguardaRespostaMiniJogo extends EstadoAdapter {
    public AguardaRespostaMiniJogo(Jogo4EmLinha jogo) {
        super(jogo);
    }

    @Override
    public IEstado recebeResposta(int resposta) {
        jogo.validaConta(resposta);
        if ((System.currentTimeMillis() - jogo.getStartTime() > 30000L) && jogo.getAcertadas() < 5){
            jogo.completeMiniGame(jogo.vezJogador1());
            jogo.setWonMiniGame(false);
            jogo.skipsTurn();
            return terminaMiniJogo();
        }

        if (jogo.getAcertadas() == 5){
            jogo.setWonMiniGame(true);
            jogo.addPecaDourada(jogo.vezJogador1());
            jogo.completeMiniGame(jogo.vezJogador1());
            return terminaMiniJogo();
        }
        else
            return new AguardaRespostaMiniJogo(jogo);
    }

    @Override
    public IEstado recebeResposta(String resposta) {
        jogo.verificaTypeRacer(resposta);

        if (jogo.getAcertadas() != 1){
            jogo.completeMiniGame(jogo.vezJogador1());
            jogo.skipsTurn();
            jogo.setWonMiniGame(false);
        }else{
            jogo.completeMiniGame(jogo.vezJogador1());
            jogo.setWonMiniGame(true);
            jogo.addPecaDourada(jogo.vezJogador1());
        }

        return terminaMiniJogo();
    }

    @Override
    public IEstado terminaMiniJogo() {

        if (jogo.wonMiniGame())
            return new AguardaJogador(jogo);

        if (!jogo.vezJogador1()){
            if (!jogo.isPlayerTwoBot() && !jogo.isPlayerTwoComplete() && jogo.getPlayerTwoRounds() == 3)
                return new AguardaMiniJogo(jogo);
        }else{
            if (!jogo.isPlayerOneBot() && !jogo.isPlayerOneComplete() && jogo.getPlayerOneRounds() == 3)
                return new AguardaMiniJogo(jogo);
        }
        return new AguardaJogador(jogo);
    }

    @Override
    public Situacao getSituacaoAtual() {
        if (jogo.getMiniGame())
            return Situacao.MINIJOGO_TYPERACER;
        else
            return Situacao.MIINIJOGO_MATHGAME;
    }

}
