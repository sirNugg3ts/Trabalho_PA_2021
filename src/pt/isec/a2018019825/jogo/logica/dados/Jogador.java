package pt.isec.a2018019825.jogo.logica.dados;

import java.io.Serializable;


public class Jogador implements Serializable {
    private static int nBots = 1;
    private final String nome;
    private int nPecasDouradas;
    private boolean isBot;
    private int creditos;

    public Jogador(String name) {
        nome = name;
        nPecasDouradas = 0;
        creditos = 5;
    }

    public Jogador(Boolean isBot) {
        this.isBot = true;
        nome = "Bot" + nBots++;
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
}
