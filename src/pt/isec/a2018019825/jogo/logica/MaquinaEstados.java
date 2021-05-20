package pt.isec.a2018019825.jogo.logica;

import pt.isec.a2018019825.jogo.logica.dados.Jogo4EmLinha;
import pt.isec.a2018019825.jogo.logica.estados.AguardaInicio;
import pt.isec.a2018019825.jogo.logica.estados.AguardaJogador;
import pt.isec.a2018019825.jogo.logica.estados.AguardaMiniJogo;
import pt.isec.a2018019825.jogo.logica.estados.IEstado;
import pt.isec.a2018019825.jogo.logica.memento.CareTaker;

public class MaquinaEstados {

    IEstado estadoAtual;
    Jogo4EmLinha jogo;
    CareTaker caretaker;

    //construtor
    public MaquinaEstados() {
        jogo = new Jogo4EmLinha();
        estadoAtual = new AguardaInicio(jogo);
        caretaker = new CareTaker(jogo);
    }


    //sets
    private void setEstadoAtual(IEstado estado) {
        this.estadoAtual = estado;
    }

    //gets

    public int getNoSnapshots(){
        return caretaker.size();
    }

    public int getCreditos(boolean player){
            return jogo.getCreditos(player);
    }

    public int getRounds() {
        return jogo.getNRounds();
    }

    public Situacao getSituacaoAtual() {
        return estadoAtual.getSituacaoAtual();
    }

    public String getWinner() {
        if (jogo.isEmpate())
            return "Empate";
        else
            return jogo.getWinnerName();
    }

    public boolean vezJogador1() {
        return jogo.vezJogador1();
    }

    public String getPlayerOneName() {
        return jogo.getNomeJogador1();
    }

    public String getPlayerTwoName() {
        return jogo.getNomeJogador2();
    }

    public int getNPecasDouradas(boolean player) {
        if (player)
            return jogo.getPecasDouradasPlayerOne();
        else
            return jogo.getPecasDouradasPlayerTwo();
    }

    public String getLogAtual(){
        return jogo.getLogAtual();
    }

    public String getAllLogs(){
        return jogo.getAllLogs();
    }


    //transition methods

    public void comeca() {
        setEstadoAtual(estadoAtual.comeca());
    }

    public boolean termina(){setEstadoAtual(estadoAtual.termina());
        caretaker.limpaStack();
        return estadoAtual == null;

    }



    public void jogaPeca(int coluna) {
        caretaker.gravaMemento();
        setEstadoAtual(estadoAtual.jogaPeca(coluna));

    }

    public void jogaPecaDourada(int coluna) {
        caretaker.gravaMemento();
        setEstadoAtual(estadoAtual.jogaPecaDourada(coluna));

    }

    public void playBot() {
        caretaker.gravaMemento();
        setEstadoAtual(estadoAtual.playBot());

    }




    public void miniJogo() {
        setEstadoAtual(estadoAtual.minijogo());
        if ((!jogo.isPlayerOneComplete() && jogo.getNRounds() == 8) || (!jogo.isPlayerTwoComplete() && jogo.getNRounds() == 9))
            setEstadoAtual(new AguardaMiniJogo(jogo));
    }

    public void ignoraMiniJogo() {
        jogo.completeMiniGame(jogo.vezJogador1());
        if ((!jogo.isPlayerOneComplete() && jogo.getNRounds() == 8) || (!jogo.isPlayerTwoComplete() && jogo.getNRounds() == 9))
            setEstadoAtual(new AguardaMiniJogo(jogo));
        setEstadoAtual(new AguardaJogador(jogo));

    }

    public boolean isNextPlayerBot() {
        return jogo.isNextPlayerBot();
    }



    //toString Methods
    public String tabuleiroToString() {
        return jogo.tabuleiroToString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Rondas Gravadas: " + caretaker.size() + "\n");
        switch (estadoAtual.getSituacaoAtual()) {
            case AGUARDA_INICIO:
                sb.append("Início de um novo jogo");
                break;
            case AGUARDA_JOGADOR1:
                sb.append(jogo.tabuleiroToString() + "\n");
                sb.append(jogo.getNomeJogador1() + ", é a tua vez de jogar!\n");
                sb.append("Peças douradas: " + jogo.getPecasDouradasPlayerOne());
                sb.append("\nCréditos: " + jogo.getCreditos(true));
                break;
            case AGUARDA_JOGADOR2:
                sb.append(jogo.tabuleiroToString() + "\n");
                sb.append(jogo.getNomeJogador2() + ", é a tua vez de jogar!\n");
                sb.append("Peças Douradas: " + jogo.getPecasDouradasPlayerTwo());
                sb.append("\nCréditos" + jogo.getCreditos(false));
                break;
            case FIM_JOGO:
                sb.append("Fim do jogo\n");
        }
        return sb.toString();
    }

    public void undo() {
        caretaker.undo();
        jogo.removeCredito(vezJogador1());
        setEstadoAtual(new AguardaJogador(jogo));
    }

    public void setJogo(String nome) {
        this.jogo = SaveAndLoad.loadGame(nome);
        setEstadoAtual(new AguardaJogador(jogo));
    }

    public void saveGame(String nome) {
        SaveAndLoad.saveState(jogo,nome);
    }
}
