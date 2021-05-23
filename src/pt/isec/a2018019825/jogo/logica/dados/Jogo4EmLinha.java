package pt.isec.a2018019825.jogo.logica.dados;

import pt.isec.a2018019825.jogo.logica.Operacao;
import pt.isec.a2018019825.jogo.logica.memento.IMementoOriginator;
import pt.isec.a2018019825.jogo.logica.memento.Memento;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Jogo4EmLinha implements Serializable, IMementoOriginator {

    ArrayList<String> historicoJogos;
    StringBuilder historicoAtual;

    private char[][] tabuleiro = new char[7][6];
    private int round;

    private Jogador playerOne, playerTwo;

    private boolean nextPlayer; //jogador1 true /jogador 2 false
    private boolean winner;
    private boolean empate;

    private final MiniJogos minijogos;

    //construtor e iniciador

    public Jogo4EmLinha() {
        historicoJogos = new ArrayList<>();
        minijogos = new MiniJogos();
    }

    public void comeca(String nomeJogador1, String nomeJogador2) {
        historicoAtual = new StringBuilder();
        //esvaziar tabuleiro
        for (char[] chars : tabuleiro) Arrays.fill(chars, ' ');
        Jogador.resetBots();

        if (nomeJogador1.isEmpty())
            playerOne = new Jogador(true);
        else
            playerOne = new Jogador(nomeJogador1);

        if (nomeJogador2.isEmpty())
            playerTwo = new Jogador(true);
        else
            playerTwo = new Jogador(nomeJogador2);

        Random random = new Random();

        //selecionar aleatoriamente o primeiro

        if (random.nextBoolean()) {
            Jogador temp = playerOne;
            playerOne = playerTwo;
            playerTwo = temp;
        }

        nextPlayer = true;
        round = 0;
        empate = false;
    }


    //jogar peças

    public void limpaColuna(int coluna) {
        Arrays.fill(tabuleiro[coluna], ' ');
        if (nextPlayer)
            playerOne.setnPecasDouradas(getPecasDouradasPlayerOne() - 1);
        else
            playerTwo.setnPecasDouradas(getPecasDouradasPlayerTwo() - 1);
        nextPlayer = !nextPlayer;

        if (round > 9) { // reset as rondas, para começar a contar de novo para mini jogo
            round = 1; //porque jogador 1 ja jogou
            minijogos.setPlayerOneComplete(false);
            minijogos.setPlayerTwoComplete(false);
        } else
            round++;
    }

    public void colocaPeca(int coluna) {
        if (vezJogador1())
            setPeca('Y', coluna);
        else
            setPeca('R', coluna);

        nextPlayer = !nextPlayer;
        if (round > 9) { // reset as rondas, para começar a contar de novo para mini jogo
            round = 1;
            minijogos.setPlayerOneComplete(false);
            minijogos.setPlayerTwoComplete(false);
        } else
            round++;
    }


    //win checker
    public boolean verificaVencedor(char jogador) {
        //verificar 4 na vertical
        for (char[] chars : tabuleiro) {
            for (int j = 0; j + 3 < chars.length; j++) {
                if (chars[j] == jogador &&
                        chars[j + 1] == jogador &&
                        chars[j + 2] == jogador &&
                        chars[j + 3] == jogador)
                    return true;
            }
        }

        //verificar 4 horizontal
        for (int i = 0; i + 3 < tabuleiro.length; i++) {
            for (int j = 0; j < tabuleiro[i].length; j++) {
                if (tabuleiro[i][j] == jogador &&
                        tabuleiro[i + 1][j] == jogador &&
                        tabuleiro[i + 2][j] == jogador &&
                        tabuleiro[i + 3][j] == jogador)
                    return true;
            }
        }

        //verificar diagonal direita
        for (int i = 0; i + 3 < tabuleiro.length; i++) {
            for (int j = 0; j + 3 < tabuleiro[i].length; j++) {
                if (tabuleiro[i][j] == jogador && tabuleiro[i + 1][j + 1] == jogador && tabuleiro[i + 2][j + 2] == jogador && tabuleiro[i + 3][j + 3] == jogador)
                    return true;
            }
        }

        //verificar diagonal esquerda
        for (int i = 3; i < tabuleiro.length; i++) {
            for (int j = 0; j + 3 < tabuleiro[i].length; j++) {
                if (tabuleiro[i][j] == jogador && tabuleiro[i - 1][j + 1] == jogador && tabuleiro[i - 2][j + 2] == jogador && tabuleiro[i - 3][j + 3] == jogador)
                    return true;
            }
        }
        return false;
    }

    public boolean tabuleiroCheio() {
        for (char[] chars : tabuleiro) {
            for (int j = 0; j < tabuleiro[j].length; j++)
                if (chars[j] == ' ')
                    return false;
        }
        return true;
    }

    //gets

    public String getNomeJogador1() {
        return playerOne.getNome();
    }

    public String getNomeJogador2() {
        return playerTwo.getNome();
    }

    public String getWinnerName() {
        if (empate)
            return "empate";
        if (winner)
            return playerOne.getNome();
        else
            return playerTwo.getNome();
    }

    public int getNRounds() {
        return round;
    }

    public int getPecasDouradasPlayerOne() {
        return playerOne.getnPecasDouradas();
    }

    public int getPecasDouradasPlayerTwo() {
        return playerTwo.getnPecasDouradas();
    }

    public int getCreditos(boolean player) {
        if (player)
            return playerOne.getCreditos();
        else
            return playerTwo.getCreditos();
    }

    public boolean colunaCheia(int coluna) {
        return tabuleiro[coluna][5] != ' ';
    }


    //sets

    private void setPeca(char y, int coluna) {
        int i = 0;
        while (tabuleiro[coluna][i] != ' ')
            i++;
        tabuleiro[coluna][i] = y;
    }

    public void removeCredito(boolean player) {
        if (player)
            playerOne.setCreditos(playerOne.getCreditos() - 1);
        else
            playerTwo.setCreditos(playerTwo.getCreditos() - 1);
    }

    public void setWinner(boolean player) {
        this.winner = player;
    }

    public void addPecaDourada(boolean player) {
        if (player)
            playerOne.setnPecasDouradas(playerOne.getnPecasDouradas() + 1);
        else
            playerTwo.setnPecasDouradas(playerTwo.getnPecasDouradas() + 1);
    }

    public void completeMiniGame(boolean jogador) {
        if (jogador)
            minijogos.setPlayerOneComplete(true);
        else
            minijogos.setPlayerTwoComplete(true);
    }


    //boolean checkers

    public boolean isEmpate() {
        return empate;
    }

    public boolean vezJogador1() {
        //devolve true para jogador1
        //devolve false para jogador2
        return nextPlayer;
    }

    public boolean isPlayerOneComplete() {
        return minijogos.isPlayerOneComplete();
    }

    public boolean isPlayerTwoComplete() {
        return minijogos.isPlayerTwoComplete();
    }

    public boolean isNextPlayerBot() {
        if (vezJogador1() && playerOne.isBot())
            return true;
        else return !vezJogador1() && playerTwo.isBot();
    }


    //funcoes log

    public void recorda(String log) {
        historicoAtual.append(tabuleiroToString() + "\n");
        historicoAtual.append("-> " + log + "\n");
    }

    public void sealLog() throws Exception {
        System.out.println("Vou escrever o ficheiro log");
        int nNewReplay = 1;
        File pastaReplays = new File("replays");

        //criar a pasta dos replays
        if (!pastaReplays.exists())
            if (!pastaReplays.mkdir())
                throw new Exception("Erro ao criar pasta de replays");

        File[] ficheiros = pastaReplays.listFiles(File::isFile);
        long lastModifiedTime = Long.MAX_VALUE;
        File oldestFile = null;

        File fold = new File("replays/replay5.txt");
        if (fold.exists())
            if (!fold.delete())
                throw new Exception("Erro ao apagar replay5.txt");


        for (int i = 4; i > 0; i--) {
            File f = new File("replays/replay" + i + ".txt");
            if (f.exists()) {
                if (!f.renameTo(new File("replays/replay" + (i + 1) + ".txt"))) {
                    throw new Exception("Erro ao criar ficheiro replay");
                }
            }
        }

        for (int i = 1; i < 6; i++) {
            File f = new File("replays/replay" + i + ".txt");
            nNewReplay = i;
            if (!f.exists()) {
                break;
            }
        }

        File f = new File("replays/replay" + nNewReplay + ".txt");

        if (f.createNewFile()) {
            FileWriter writer = new FileWriter(f);
            writer.write(historicoAtual.toString());
            writer.close();
        }

        historicoJogos.add(historicoAtual.toString());
    }

    public String getLogAtual() {
        return historicoAtual.toString();
    }

    public String getAllLogs() {
        StringBuilder sb = new StringBuilder();
        for (String potato : historicoJogos)
            sb.append(potato);
        sb.append("\n--------");
        return sb.toString();
    }


    //toString

    public String tabuleiroToString() {
        StringBuilder sb = new StringBuilder();
        sb.append("---------------\n");
        for (int j = tabuleiro[0].length - 1; j >= 0; j--) {
            sb.append("|");
            for (int i = 0; i < tabuleiro.length; i++) {
                sb.append(tabuleiro[i][j]);
                sb.append("|");
            }
            sb.append("\n");
            sb.append("---------------\n");
        }

        sb.append("|0|1|2|3|4|5|6\n");
        sb.append("\n");

        return sb.toString();
    }


    //funções mementos
    @Override
    public Memento getMemento() throws IOException {
        //a contagem de grupos de 4 jogadas para acesso aos mini-jogos é colocada a zero.
        return new Memento(new Object[]{tabuleiro, nextPlayer});
    }

    @Override
    public void setMemento(Memento m) throws IOException, ClassNotFoundException {
        Object[] objects = (Object[]) m.getSnapShot();
        this.tabuleiro = (char[][]) objects[0];
        this.nextPlayer = (boolean) objects[1];
        this.round = 0;
    }

    //funcoes minijogos

    public boolean getMiniGame() {
        return minijogos.getMiniGame();
    }

    public void startClock() {
        minijogos.startClock();
    }

    public long getStartTime() {
        return minijogos.getTime();
    }

    public int getAcertadas() {
        return minijogos.getAcertadas();
    }

    public boolean sealGame() {
        if (minijogos.sealGame()) {
            addPecaDourada(vezJogador1());
            if (vezJogador1())
                recorda(playerOne.getNome() + " venceu o minijogo");
            else
                recorda(playerTwo.getNome() + " venceu o minijogo");
            return true;
        }
        if (vezJogador1())
            recorda(playerOne.getNome() + " perdeu o minijogo");
        else
            recorda(playerTwo.getNome() + " perdeu o minijogo");
        skipsTurn();
        return false;
    }

    //typeracer

    public String getQuestaoTypeRacer() {
        return minijogos.getQuestaoTypeRacer();
    }

    public void verificaTypeRacer(String resposta) {
        minijogos.verificaTypeRacer(resposta);
    }

    //math game

    public Operacao getMathGameOperation() {
        return minijogos.getOperation();
    }

    public int getMiniJogoN1() {
        return minijogos.getNumber1();
    }

    public int getMiniJogoN2() {
        return minijogos.getNumber2();
    }

    public boolean validaConta(int resposta) {
        return minijogos.validaConta(resposta);
    }


    public void criaQuestaoMatematica() {
        minijogos.criaQuestaoMatematica();
    }


    //others
    public void skipsTurn() {
        nextPlayer = !nextPlayer;
        round++;
    }

}
