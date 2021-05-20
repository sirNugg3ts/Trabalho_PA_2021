package pt.isec.a2018019825.jogo.logica.dados;


import pt.isec.a2018019825.jogo.Utils.Utils;
import pt.isec.a2018019825.jogo.logica.memento.IMementoOriginator;
import pt.isec.a2018019825.jogo.logica.memento.Memento;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Jogo4EmLinha implements Serializable, IMementoOriginator {
    ArrayList<String> historicoJogos;
    StringBuilder historicoAtual;
    private char[][] tabuleiro = new char[7][6];
    private int round;

    private Jogador playerOne, playerTwo;

    private boolean nextPlayer; //jogador1 true /jogador 2 false
    private boolean winner;
    private boolean empate;

    private MiniJogos minijogos;



    //construtor e iniciador

    public Jogo4EmLinha() {
        historicoJogos = new ArrayList<>();
    }

    public void comeca() {
        historicoAtual = new StringBuilder();
        //esvaziar tabuleiro
        for (char[] chars : tabuleiro) Arrays.fill(chars, ' ');
        Jogador.resetBots();

        int nBots;
        do {
            nBots = Utils.pedeInteiro("Indique quantos bots irão jogar (0 - 1 - 2):");
            if (nBots < 0 || nBots > 2) {
                System.out.println("Valor inválido");
            }
        } while (nBots < 0 || nBots > 2);

        switch (nBots) {
            case 0:
                playerOne = new Jogador(obtemNomeJogador(0));
                playerTwo = new Jogador(obtemNomeJogador(1));
                break;
            case 1:
                playerOne = new Jogador(obtemNomeJogador(0));
                playerTwo = new Jogador(true);
                break;
            case 2:
                playerOne = new Jogador(true);
                playerTwo = new Jogador(true);
                break;
        }

        nextPlayer = true;
        round = 0;
        empate = false;

        minijogos = new MiniJogos();
    }

    //normal methods

    private String obtemNomeJogador(int nJogador) {
        Scanner sc = new Scanner(System.in);
        String name;
        do {
            if (nJogador == 0)
                System.out.print("Indique o nome do primeiro jogador: ");
            else
                System.out.print("Indique o nome do segundo jogador: ");
            name = sc.nextLine().trim();
        } while (name.isEmpty());
        return name;
    }

    public void limpaColuna(int coluna) {
        Arrays.fill(tabuleiro[coluna], ' ');
        if (nextPlayer)
            playerOne.setnPecasDouradas(getPecasDouradasPlayerOne() - 1);
        else
            playerTwo.setnPecasDouradas(getPecasDouradasPlayerTwo() - 1);
        nextPlayer = !nextPlayer;

        if (round > 9) { // reset as rondas, para comecar a contar de novo para minijogo
            round = 1;
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
        if (round > 9) { // reset as rondas, para comecar a contar de novo para minijogo
            round = 1;
            minijogos.setPlayerOneComplete(false);
            minijogos.setPlayerTwoComplete(false);
        } else
            round++;
    }

    public boolean colunaCheia(int coluna) {
        return tabuleiro[coluna][5] != ' ';
    }

    public void skipsTurn() {
        nextPlayer = !nextPlayer;
        round++;
    }


    //run minigames

    public boolean startMathGame() {
        return minijogos.mathGame();
    }

    public boolean startTypeRacer() {
        return minijogos.typeRacer();
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

    public int getCreditos(boolean player){
        if (player)
            return playerOne.getCreditos();
        else
            return playerTwo.getCreditos();
    }

    //sets

    private void setPeca(char y, int coluna) {
        int i = 0;
        while (tabuleiro[coluna][i] != ' ')
            i++;
        tabuleiro[coluna][i] = y;
    }

    public void removeCredito(boolean player){
        if (player)
            playerOne.setCreditos(playerOne.getCreditos()-1);
        else
            playerTwo.setCreditos(playerTwo.getCreditos()-1);
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

    public boolean isTypeRacerEnabled() {
        return minijogos.isTypeRacerEnabled();
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




    public void recorda(String log){
        historicoAtual.append( "-> " + log + "\n");
    }

    public void sealLog(){
        historicoJogos.add(historicoAtual.toString());
    }

    public String getLogAtual(){
        return historicoAtual.toString();
    }

    public ArrayList<String> listaHistoricosJogos(){
        return historicoJogos;
    }

    public String getAllLogs(){
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

    @Override
    public Memento getMemento() throws IOException {
         //a contagem de grupos de 4 jogadas para acesso aos mini-jogos é colocada a zero.
        return new Memento(new Object[] {tabuleiro,nextPlayer});
    }

    @Override
    public void setMemento(Memento m) throws IOException, ClassNotFoundException {
       Object[] objects = (Object[]) m.getSnapShot();
       this.tabuleiro = (char[][]) objects[0];
       this.nextPlayer = (boolean) objects[1];
       this.round = 0;
    }
}
