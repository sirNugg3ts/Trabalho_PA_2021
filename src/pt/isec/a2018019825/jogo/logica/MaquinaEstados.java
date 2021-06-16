package pt.isec.a2018019825.jogo.logica;

import pt.isec.a2018019825.jogo.logica.dados.Jogo4EmLinha;
import pt.isec.a2018019825.jogo.logica.estados.AguardaInicio;
import pt.isec.a2018019825.jogo.logica.estados.AguardaJogador;
import pt.isec.a2018019825.jogo.logica.estados.IEstado;
import pt.isec.a2018019825.jogo.logica.estados.Replay;
import pt.isec.a2018019825.jogo.logica.memento.CareTaker;

import java.io.*;

public class MaquinaEstados {

    private IEstado estadoAtual;
    private Jogo4EmLinha jogo;
    private CareTaker caretaker;

    //replay
    private CareTaker tempCaretaker;
    private Jogo4EmLinha tempJogo;

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

    public int getNoSnapshots() {
        return caretaker.size();
    }

    public int getCreditos(boolean player) {
        return jogo.getCreditos(player);
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

    public String getLogAtual() {
        return jogo.getLogAtual();
    }

    public String getAllLogs() {
        return jogo.getAllLogs();
    }

    public boolean colunaCheia(int coluna){
        return jogo.colunaCheia(coluna);
    }

    public boolean isNextPlayerBot() {
        return jogo.isNextPlayerBot();
    }

    public char[][] getTabuleiro(){
        return jogo.getTabuleiro();
    }

    public boolean isTabuleiroCheio() {
        return jogo.tabuleiroCheio();
    }

    public boolean getMiniGame() {
        return jogo.getMiniGame();
    }

    public int getRondas() {
        return vezJogador1() ? jogo.getPlayerOneRounds() : jogo.getPlayerTwoRounds();
    }


    //transition methods

    public void comeca(String nomeJogador1, String nomeJogador2) {
        setEstadoAtual(estadoAtual.comeca(nomeJogador1, nomeJogador2));
    }

    public void termina(boolean restart) {
        setEstadoAtual(estadoAtual.termina(restart));
        caretaker.limpaStack();
    }

    public void miniJogo() {
        setEstadoAtual(estadoAtual.minijogo());
    }

    public void startMiniGame() {
        setEstadoAtual(estadoAtual.startMiniGame());
    }

    public void ignoraMiniJogo() {
        setEstadoAtual(estadoAtual.ignoraMiniJogo());
    }


    public int jogaPeca(int coluna) throws Exception {

        if (jogo.colunaCheia(coluna))
            return -1;
        caretaker.gravaMemento();
        setEstadoAtual(estadoAtual.jogaPeca(coluna));
        if (estadoAtual.getSituacaoAtual() == Situacao.FIM_JOGO){
            gravaReplay();
        }
        return 0;
    }

    public void playBot() throws Exception {
        caretaker.gravaMemento();
        setEstadoAtual(estadoAtual.playBot());
        if (estadoAtual.getSituacaoAtual() == Situacao.FIM_JOGO){
            gravaReplay();
        }
    }

    public void jogaPecaDourada(int coluna) throws IOException {
        caretaker.gravaMemento();
        setEstadoAtual(estadoAtual.jogaPecaDourada(coluna));

    }




   //ToString

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
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

    public String tabuleiroToString(){
        return jogo.tabuleiroToString();
    }

    //Voltar Atras

    public void undo(boolean jogador) throws Exception {
        caretaker.undo();
        jogo.removeCredito(jogador);
        setEstadoAtual(new AguardaJogador(jogo));
    }

    //Carregar Jogo - In Progress

    public void setJogo(File file) throws Exception {
        Object[] objects = SaveAndLoad.loadGame(file);
        this.jogo = (Jogo4EmLinha) objects[0];
        this.caretaker = (CareTaker) objects[1];
        setEstadoAtual(new AguardaJogador(jogo));
    }

    public void saveGame(File nome) throws IOException {
        SaveAndLoad.saveState(jogo, caretaker, nome);
    }


    private void gravaReplay() throws Exception {
        int nNewReplay = 1;
        File pastaReplays = new File("replays");

        //criar a pasta dos replays
        if (!pastaReplays.exists())
            if (!pastaReplays.mkdir())
                throw new Exception("Erro ao criar pasta de replays");

        File fold = new File("replays/replay5.dat");
        if (fold.exists())
            if (!fold.delete())
                throw new Exception("Erro ao apagar replay5");


        for (int i = 4; i > 0; i--) {
            File f = new File("replays/replay" + i + ".dat");
            if (f.exists()) {
                if (!f.renameTo(new File("replays/replay" + (i + 1) + ".dat"))) {
                    throw new Exception("Erro ao criar ficheiro replay");
                }
            }
        }

        for (int i = 1; i < 6; i++) {
            File f = new File("replays/replay" + i + ".dat");
            nNewReplay = i;
            if (!f.exists()) {
                break;
            }
        }

        File f = new File("replays/replay" + nNewReplay + ".dat");

        if (f.createNewFile()) {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));

            Object object = caretaker;
            oos.writeObject(object);
            oos.close();
        }

    }



    //minijogo matematica
    public String getQuestaoMath() {
        jogo.criaQuestaoMatematica();
        switch (jogo.getMathGameOperation()) {
            case SOMA:
                return jogo.getMiniJogoN1() + "+" + jogo.getMiniJogoN2();
            case SUB:
                return jogo.getMiniJogoN1() + "-" + jogo.getMiniJogoN2();
            case MULT:
                return jogo.getMiniJogoN1() + "*" + jogo.getMiniJogoN2();
            case DIV:
                return jogo.getMiniJogoN1() + "/" + jogo.getMiniJogoN2();
        }
        return null;
    }

    public int validaConta(int resposta) {

        setEstadoAtual(estadoAtual.recebeResposta(resposta));
        if (estadoAtual.getSituacaoAtual() != Situacao.MIINIJOGO_MATHGAME && !jogo.wonMiniGame())
            return -1;
        else if (estadoAtual.getSituacaoAtual() != Situacao.MIINIJOGO_MATHGAME && jogo.wonMiniGame())
            return 2;
         else{
             if (jogo.isLastAnswerCorrect())
                 return 1;
             else
                 return 0;
         }
    }

    public String getQuestaoTypeRacer() {
        return jogo.getQuestaoTypeRacer();
    }

    public void verificaTypeRacer(String resposta) {
        setEstadoAtual(estadoAtual.recebeResposta(resposta));
    }

    public int getAcertados() {
        return jogo.getAcertadas();
    }

}
