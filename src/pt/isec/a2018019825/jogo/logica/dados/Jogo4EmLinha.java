package pt.isec.a2018019825.jogo.logica.dados;


import java.util.Arrays;
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

        playerOne = new Jogador(obtemNomeJogador(0));
        playerTwo = new Jogador(obtemNomeJogador(1));
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

    public void colocaPeca(int coluna) {
        if (vezJogador1())
            setPeca('Y', coluna);
        else
            setPeca('R', coluna);

        nextPlayer = !nextPlayer;
        round++;
    }

    public boolean colunaCheia(int coluna){
        if (tabuleiro[coluna][5] == ' ')
            return false;
        return true;
    }

    public void skipsTurn(){nextPlayer = !nextPlayer;}


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

    public boolean isTypeRacerEnabled(){
        return minijogos.isTypeRacerEnabled();
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
