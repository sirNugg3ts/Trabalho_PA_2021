package pt.isec.a2018019825.jogo.logica.dados;

import pt.isec.a2018019825.jogo.logica.Operacao;
import pt.isec.a2018019825.jogo.logica.memento.IMementoOriginator;
import pt.isec.a2018019825.jogo.logica.memento.Memento;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Jogo4EmLinha implements Serializable, IMementoOriginator {

    ArrayList<String> historicoJogos;
    StringBuilder historicoAtual;

    private char[][] tabuleiro = new char[7][6];

    private Jogador playerOne, playerTwo;

    private boolean nextPlayer; //jogador1 true /jogador 2 false
    private boolean winner;
    private boolean empate;
    private boolean jogoAcabou;
    private boolean lastAnswerCorrect;
    private boolean wonMiniGame;

    public boolean isWonMiniGame() {
        return wonMiniGame;
    }

    public void setWonMiniGame(boolean wonMiniGame) {
        this.wonMiniGame = wonMiniGame;
    }



    private final MiniJogos minijogos;

    //construtor e iniciador

    public Jogo4EmLinha() {
        historicoJogos = new ArrayList<>();
        minijogos = new MiniJogos();
        playerOne = new Jogador(true);
        playerTwo = new Jogador(true);
        jogoAcabou = false;
    }

    public char[][] getTabuleiro(){
        return tabuleiro;
    }

    public void comeca(String nomeJogador1, String nomeJogador2) {
        Random random = new Random();
        historicoAtual = new StringBuilder();
        //esvaziar tabuleiro
        for (char[] chars : tabuleiro) Arrays.fill(chars, ' ');
        Jogador.resetBots();

        if (nomeJogador1.isEmpty())
            playerOne = new Jogador(true);
        else{
            if (minijogos.isTypeRacerEnabled())
                playerOne = new Jogador(nomeJogador1,random.nextBoolean());
            else
                playerOne = new Jogador(nomeJogador1,false);
        }

        if (nomeJogador2.isEmpty())
            playerTwo = new Jogador(true);
        else{
            if (minijogos.isTypeRacerEnabled())
                playerTwo = new Jogador(nomeJogador2,random.nextBoolean());
            else
                playerTwo = new Jogador(nomeJogador2,false);
        }

        //selecionar aleatoriamente o primeiro

        if (random.nextBoolean()) {
            Jogador temp = playerOne;
            playerOne = playerTwo;
            playerTwo = temp;
        }

        nextPlayer = true;
        playerOne.setRondas(0);
        playerTwo.setRondas(0);
        empate = false;
    }


    //jogar peças

    public void limpaColuna(int coluna) {
        Arrays.fill(tabuleiro[coluna], ' ');
        if (vezJogador1()){
            playerOne.setnPecasDouradas(getPecasDouradasPlayerOne() - 1);
            if (playerOne.getRondas() == 3){
                playerOne.setRondas(1);
                playerOne.setMiniGameComplete(false);
        }
            else
                playerOne.aumentaJogada();

        }else{
            playerTwo.setnPecasDouradas(getPecasDouradasPlayerTwo() - 1);
            if (playerTwo.getRondas() == 3){
                playerTwo.setRondas(1);
                playerTwo.setMiniGameComplete(false);
            }
            else
                playerTwo.aumentaJogada();
        }
        nextPlayer = !nextPlayer;
    }

    public void colocaPeca(int coluna) {
        if (vezJogador1())
            setPeca('Y', coluna);
        else
            setPeca('R', coluna);

        if (vezJogador1()){
            if (playerOne.getRondas() == 3){
                playerOne.setRondas(1);
                playerOne.setMiniGameComplete(false);
            }
            else
                playerOne.aumentaJogada();
        }else{
            if (playerTwo.getRondas() == 3){
                playerTwo.setRondas(1);
                playerTwo.setMiniGameComplete(false);
            }
            else
                playerTwo.aumentaJogada();
        }
        nextPlayer = !nextPlayer;
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
        this.jogoAcabou = true;
    }

    public void addPecaDourada(boolean player) {
        if (player)
            playerOne.setnPecasDouradas(playerOne.getnPecasDouradas() + 1);
        else
            playerTwo.setnPecasDouradas(playerTwo.getnPecasDouradas() + 1);
    }

    public void completeMiniGame(boolean jogador) {
        if (jogador){
            playerOne.setMiniGameComplete(true);
            if (minijogos.isTypeRacerEnabled())
                playerOne.invertNextMiniGame();
        }
        else{
            playerTwo.setMiniGameComplete(true);
            if (minijogos.isTypeRacerEnabled())
                playerTwo.invertNextMiniGame();
        }

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
        return playerOne.getMiniGameComplete();
    }

    public boolean isPlayerTwoComplete() {
        return playerTwo.getMiniGameComplete();
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
        return new Memento(new Object[]{tabuleiro, nextPlayer,playerOne,playerTwo});
    }

    @Override
    public void setMemento(Memento m) throws IOException, ClassNotFoundException {
        Object[] objects = (Object[]) m.getSnapShot();
        this.tabuleiro = (char[][]) objects[0];
        this.nextPlayer = (boolean) objects[1];
    }


    //funcoes minijogos

    public boolean getMiniGame() {
        if (vezJogador1())
            return playerOne.getMiniJogo();
        else
            return playerTwo.getMiniJogo();
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

    public void validaConta(int resposta) {
        if (minijogos.validaConta(resposta)){
            lastAnswerCorrect = true;
        }else
            lastAnswerCorrect = false;

    }


    public void criaQuestaoMatematica() {
        minijogos.criaQuestaoMatematica();
    }


    //others
    public void skipsTurn() {
        if (vezJogador1()){
            playerOne.aumentaJogada();
            if (playerOne.getRondas() == 4)
                playerOne.setRondas(0);
        }

        else{
            playerTwo.aumentaJogada();
            if (playerTwo.getRondas() == 4)
                playerTwo.setRondas(0);
        }





        nextPlayer = !nextPlayer;



    }


    public int getPlayerTwoRounds() {
        return playerTwo.getRondas();
    }

    public int getPlayerOneRounds() {
        return playerOne.getRondas();
    }

    public boolean wonMiniGame() {

        return wonMiniGame;
    }

    public boolean isPlayerOneBot() {
        return playerOne.isBot();
    }

    public boolean isPlayerTwoBot() {
        return playerTwo.isBot();
    }

    public boolean isLastAnswerCorrect() {
        return lastAnswerCorrect;
    }

    public void setLastAnswerCorrect(boolean lastAnswerCorrect) {
        this.lastAnswerCorrect = lastAnswerCorrect;
    }

    public void acabaJogo() {
        jogoAcabou = true;
    }

    public boolean isFinishedGame() {
        return jogoAcabou;
    }

    public void endgame() {
        this.jogoAcabou = true;
    }
}
