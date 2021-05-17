package pt.isec.a2018019825.jogo.logica.dados;


import pt.isec.a2018019825.jogo.Utils.Utils;
import pt.isec.a2018019825.jogo.logica.estados.AguardaJogador;
import pt.isec.a2018019825.jogo.logica.estados.FimJogo;
import pt.isec.a2018019825.jogo.logica.estados.IEstado;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Jogo4EmLinha {
    private char[][] tabuleiro = new char[7][6];
    private int round;

    private Jogador playerOne,playerTwo;

    private boolean nextPlayer; //jogador1 true /jogador 2 false
    private boolean winner;
    private boolean empate;

    private MiniJogos minijogos;

    public Jogo4EmLinha() {}

    public void comeca() {
        //esvaziar tabuleiro
        for (char[] chars : tabuleiro) Arrays.fill(chars, ' ');
        Jogador.resetBots();

        int nBots = -1;
        do{
            nBots = Utils.pedeInteiro("Indique quantos bots irão jogar (0 - 1 - 2):");
            if (nBots<0 || nBots>2){
                System.out.println("Valor inválido");
            }
        }while(nBots<0 || nBots>2);

        switch (nBots){
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
        for (int i = 0;i < tabuleiro[coluna].length;i++)
            tabuleiro[coluna][i] = ' ';
        if (nextPlayer)
            playerOne.setnPecasDouradas(getPecasDouradasPlayerOne()-1);
        else
            playerTwo.setnPecasDouradas(getPecasDouradasPlayerTwo()-1);
        nextPlayer = !nextPlayer;

        if (round > 9) { // reset as rondas, para comecar a contar de novo para minijogo
            round = 1;
            minijogos.setPlayerOneComplete(false);
            minijogos.setPlayerTwoComplete(false);
        }
        else
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
        }
        else
            round++;
    }

    public boolean colunaCheia(int coluna){
        if (tabuleiro[coluna][5] == ' ')
            return false;
        return true;
    }

    public void skipsTurn(){nextPlayer = !nextPlayer;round++;}

    public void completeMiniGame(boolean jogador){
        if (jogador)
            minijogos.setPlayerOneComplete(true);
        else
            minijogos.setPlayerTwoComplete(true);
    }

    public IEstado playBot(){
        if (vezJogador1() && playerOne.isBot()){
            //jogar
            Random random = new Random();
            int x;
            do{
                x = random.nextInt(6);
            }while (colunaCheia(x));

            colocaPeca(x);

            //verificar proximo estado

            if (verificaVencedor('Y')){ //player 1 ganhou
                setWinner(true);
                return new FimJogo(this);
            }else if(verificaVencedor('R')){//player 2 ganhou
                setWinner(false);
                return new FimJogo(this);
            }
            if (tabuleiroCheio()) // ta cheio, e ninguem ganhou, logo empate
                return new FimJogo(this);
        }
        else if(!vezJogador1() && playerTwo.isBot()){
            //jogar

            //jogar
            Random random = new Random();
            int x;
            do{
                x = random.nextInt(6);
            }while (colunaCheia(x));

            colocaPeca(x);

            //verificar proximo estado

            if (verificaVencedor('Y')){ //player 1 ganhou
                setWinner(true);
                return new FimJogo(this);
            }else if(verificaVencedor('R')){//player 2 ganhou
                setWinner(false);
                return new FimJogo(this);
            }

            if (tabuleiroCheio()) // ta cheio, e ninguem ganhou, logo empate
                return new FimJogo(this);
        }
        else{
            System.err.println("Not supposed to happen, bad programming");
            return null;
        }
        return new AguardaJogador(this);
    }



    //run minigames

    public boolean startMathGame(){return minijogos.mathGame();}
    public boolean startTypeRacer(){return minijogos.typeRacer();}



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

    public String getWinnerName(){
        if(empate)
            return "empate";
        if (winner)
            return playerOne.getNome();
        else
            return playerTwo.getNome();
    }

    public String getNomeJogador2() {
        return playerTwo.getNome();
    }

    public int getNRounds() {
        return round;
    }

    public int getPecasDouradasPlayerOne(){
        return playerOne.getnPecasDouradas();
    }

    public int getPecasDouradasPlayerTwo(){
        return playerTwo.getnPecasDouradas();
    }

    //sets

    private void setPeca(char y, int coluna) {
        int i = 0;
        while (tabuleiro[coluna][i] != ' ')
            i++;
        tabuleiro[coluna][i] = y;
    }

    public void setWinner(boolean player){
        this.winner = player;
    }

    public void addPecaDourada(boolean player){
        if (player)
            playerOne.setnPecasDouradas(playerOne.getnPecasDouradas()+1);
        else
            playerTwo.setnPecasDouradas(playerTwo.getnPecasDouradas()+1);
    }

    //boolean checkers

    public boolean isEmpate(){return empate;};

    public boolean vezJogador1() {
        //devolve true para jogador1
        //devolve false para jogador2
        return nextPlayer;
    }

    public boolean isTypeRacerEnabled(){return minijogos.isTypeRacerEnabled();}
    public boolean isPlayerOneComplete(){return minijogos.isPlayerOneComplete();}
    public boolean isPlayerTwoComplete(){return minijogos.isPlayerTwoComplete();}
    public boolean isNextPlayerBot(){
        if (vezJogador1() && playerOne.isBot())
            return true;
        else if (!vezJogador1() && playerTwo.isBot())
            return true;
        else
            return false;
    }

    //

    public String tabuleiroToString(){
        StringBuilder sb = new StringBuilder();
        sb.append("---------------\n");
       for (int j=tabuleiro[0].length-1;j>=0;j--){
            sb.append("|");
            for (int i=0;i < tabuleiro.length;i++){
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



}
