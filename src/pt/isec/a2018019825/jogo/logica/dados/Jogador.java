package pt.isec.a2018019825.jogo.logica.dados;

public class Jogador {
    private static int nBots = 0;
    private String nome;
    private int nPecasDouradas;
    private boolean isBot;

    public Jogador(String name){
        nome = name;
        nPecasDouradas = 0;
    }

    public Jogador(Boolean isBot){
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

    public void setnPecasDouradas(int nPecasDouradas) {
        this.nPecasDouradas = nPecasDouradas;
    }

    public void setBot(boolean bot) {
        isBot = bot;
    }
}
