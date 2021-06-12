package pt.isec.a2018019825.jogo.logica.dados;

import java.io.Serializable;


public class Jogador implements Serializable {
    private static int nBots = 1;
    private final String nome;
    private int nPecasDouradas;
    private boolean isBot;
    private int creditos;
    private int rondas;
    private boolean nextMiniGame;
    private boolean minigameComplete;

    public Jogador(String name,boolean jogoAtribuido) {
        nome = name;
        nPecasDouradas = 0;
        creditos = 5;
        rondas = 0;
        this.nextMiniGame = jogoAtribuido;
        minigameComplete = false;
        this.isBot = false;
    }

    public Jogador(Boolean isBot) {
        this.isBot = true;
        nome = "Bot" + nBots++;
        minigameComplete = true;
    }

    public String getNome() {
        return nome;
    }

    public int getnPecasDouradas() {
        return nPecasDouradas;
    }

    public boolean isBot() {
        return isBot;
    }

    public static void resetBots() {
        nBots = 1;
    }

    public void setnPecasDouradas(int nPecasDouradas) {
        this.nPecasDouradas = nPecasDouradas;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public void aumentaJogada() {
        rondas++;
    }

    public int getRondas() {
        return this.rondas;
    }

    public void setRondas(int n){
        this.rondas = n;
    }

    public boolean getMiniJogo() {
        return this.nextMiniGame;
    }

    public void setMiniGameComplete(boolean b) {
        this.minigameComplete = b;
    }

    public boolean getMiniGameComplete(){
        return this.minigameComplete;
    }

    public void invertNextMiniGame() {
        this.nextMiniGame = !nextMiniGame;
    }
}
