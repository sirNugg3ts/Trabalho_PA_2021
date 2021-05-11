package pt.isec.a2018019825.jogo.logica;

import pt.isec.a2018019825.jogo.logica.dados.Jogo4EmLinha;
import pt.isec.a2018019825.jogo.logica.estados.AguardaInicio;
import pt.isec.a2018019825.jogo.logica.estados.IEstado;

public class MaquinaEstados {

    IEstado estadoAtual;
    Jogo4EmLinha jogo;

    public MaquinaEstados(){
        jogo = new Jogo4EmLinha();
        estadoAtual = new AguardaInicio(jogo);
    }

    private void setEstadoAtual(IEstado estado){
        this.estadoAtual = estado;
    }

    public String tabuleiroToString(){
        return jogo.tabuleiroToString();
    }

    public void comeca(){
        setEstadoAtual(estadoAtual.comeca());
    }

    public void jogaPeca(int coluna){
        setEstadoAtual(estadoAtual.jogaPeca(coluna));
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


    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        switch (estadoAtual.getSituacaoAtual()){
            case AGUARDA_INICIO:
                sb.append("Início de um novo jogo");
                break;
            case AGUARDA_JOGADOR1:
                sb.append(jogo.tabuleiroToString()+"\n");
                sb.append(jogo.getNomeJogador1() + ", é a tua vez de jogar!");
                break;
            case AGUARDA_JOGADOR2:
                sb.append(jogo.tabuleiroToString()+"\n");
                sb.append(jogo.getNomeJogador2() + ", é a tua vez de jogar!\n");
                break;
            case FIM_JOGO:
                sb.append("Fim do jogo\n");
        }
        return sb.toString();
    }
}
