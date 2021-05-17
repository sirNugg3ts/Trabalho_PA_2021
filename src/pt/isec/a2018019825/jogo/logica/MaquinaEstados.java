package pt.isec.a2018019825.jogo.logica;

import pt.isec.a2018019825.jogo.logica.dados.Jogo4EmLinha;
import pt.isec.a2018019825.jogo.logica.estados.AguardaInicio;
import pt.isec.a2018019825.jogo.logica.estados.AguardaJogador;
import pt.isec.a2018019825.jogo.logica.estados.AguardaMiniJogo;
import pt.isec.a2018019825.jogo.logica.estados.IEstado;

public class MaquinaEstados {

    IEstado estadoAtual;
    Jogo4EmLinha jogo;

    //construtor
    public MaquinaEstados(){
        jogo = new Jogo4EmLinha();
        estadoAtual = new AguardaInicio(jogo);
    }


    //sets
    private void setEstadoAtual(IEstado estado){
        this.estadoAtual = estado;
    }

    //gets

    public int getRounds(){
        return jogo.getNRounds();
    }

    public Situacao getSituacaoAtual(){
        return estadoAtual.getSituacaoAtual();
    }

    public String getWinner(){
        if(jogo.isEmpate())
            return "Empate";
        else
            return jogo.getWinnerName();
    }

    public boolean nextPlayerOne(){
        return jogo.vezJogador1();
    }

    public String getPlayerOneName(){
        return jogo.getNomeJogador1();
    }

    public String getPlayerTwoName(){
        return jogo.getNomeJogador2();
    }

    public int getNPecasDouradas(boolean player){
        if (player)
            return jogo.getPecasDouradasPlayerOne();
        else
            return jogo.getPecasDouradasPlayerTwo();
    }


    //transition methods

    public void comeca(){
        setEstadoAtual(estadoAtual.comeca());
    }

    public void jogaPeca(int coluna){
            setEstadoAtual(estadoAtual.jogaPeca(coluna));
        if (((!jogo.isPlayerOneComplete() && jogo.getNRounds() == 8) || (!jogo.isPlayerTwoComplete() && jogo.getNRounds() == 9)) && !jogo.isNextPlayerBot())
            setEstadoAtual(new AguardaMiniJogo(jogo));
    }

    public void jogaPecaDourada(int coluna){
        setEstadoAtual(estadoAtual.jogaPecaDourada(coluna));
    }

    public void miniJogo(){
        setEstadoAtual(estadoAtual.minijogo());
        if ((!jogo.isPlayerOneComplete() && jogo.getNRounds() == 8) || (!jogo.isPlayerTwoComplete() && jogo.getNRounds() == 9))
            setEstadoAtual(new AguardaMiniJogo(jogo));
    }

    public void ignoraMiniJogo(){
        jogo.completeMiniGame(jogo.vezJogador1());
        if ((!jogo.isPlayerOneComplete() && jogo.getNRounds() == 8) || (!jogo.isPlayerTwoComplete() && jogo.getNRounds() == 9))
            setEstadoAtual(new AguardaMiniJogo(jogo));
        setEstadoAtual(new AguardaJogador(jogo));

    }

    public boolean isNextPlayerBot(){
        return jogo.isNextPlayerBot();
    }

    public void playBot(){
        setEstadoAtual(jogo.playBot());
        if (((!jogo.isPlayerOneComplete() && jogo.getNRounds() == 8) || (!jogo.isPlayerTwoComplete() && jogo.getNRounds() == 9)) && !jogo.isNextPlayerBot())
            setEstadoAtual(new AguardaMiniJogo(jogo));
    }

    //toString Methods
    public String tabuleiroToString(){
        return jogo.tabuleiroToString();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        switch (estadoAtual.getSituacaoAtual()){
            case AGUARDA_INICIO:
                sb.append("Início de um novo jogo");
                break;
            case AGUARDA_JOGADOR1:
                sb.append(jogo.tabuleiroToString()+"\n");
                sb.append(jogo.getNomeJogador1() + ", é a tua vez de jogar!\n");
                sb.append("Peças douradas: " + jogo.getPecasDouradasPlayerOne());
                break;
            case AGUARDA_JOGADOR2:
                sb.append(jogo.tabuleiroToString()+"\n");
                sb.append(jogo.getNomeJogador2() + ", é a tua vez de jogar!\n");
                sb.append("Peças Douradas: " + jogo.getPecasDouradasPlayerTwo());
                break;
            case FIM_JOGO:
                sb.append("Fim do jogo\n");
        }
        return sb.toString();
    }
}
