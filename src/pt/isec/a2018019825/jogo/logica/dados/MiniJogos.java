package pt.isec.a2018019825.jogo.logica.dados;


import pt.isec.a2018019825.jogo.logica.Operacao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.*;


public class MiniJogos extends Jogo4EmLinha implements Serializable {

    private boolean typeRacerEnabled;
    private boolean playerOneComplete, playerTwoComplete;
    private boolean nextGame; //true -> typeRacer
                              //false -> MathGame

    private long start;
    private int acertados;
    //math game
    private int numero1,numero2;
    private Operacao operacao;
    //text game
    private ArrayList<String> listaPalavras;
    private int palavrasLidas;
    private String fraseEscolhidaTypeRacer;



    public MiniJogos() {
        palavrasLidas = 0;
        playerOneComplete = false;
        playerTwoComplete = false;
        nextGame = false; //comecamos sempre no math game

        //obter lista de palavras para o typeracer
        File ficheiroTypeRacer = new File("typeracer.txt");
        if (!ficheiroTypeRacer.canRead()) {
            System.err.println("Erro ao ler ficheiro texto typeracer.txt para minijogos");
            System.err.println("O programa irá continuar sem este minijogo");
            typeRacerEnabled = false;
        } else {
            try {
                Scanner sc = new Scanner(ficheiroTypeRacer);
                listaPalavras = new ArrayList<>();
                while (sc.hasNextLine()) {
                    String novaPalavra = sc.nextLine();
                    novaPalavra = novaPalavra.trim().toLowerCase(Locale.ROOT);
                    if (novaPalavra.length() >= 5){
                        listaPalavras.add(novaPalavra);
                        palavrasLidas++;
                    }else{
                        System.out.println(novaPalavra + " não é uma palavra válida");
                    }
                }
                System.out.println("Ficheiro texto lido com sucesso: " + palavrasLidas + " palavras lidas");
                typeRacerEnabled = true;
                sc.close();
            } catch (FileNotFoundException e) {
                System.err.println("Ok não era suposto chegar aqui");
                typeRacerEnabled = false;
            }
        }
    }


    //boolean checkers

    public boolean isPlayerOneComplete() {
        return playerOneComplete;
    }

    public boolean isPlayerTwoComplete() {
        return playerTwoComplete;
    }

    //sets

    public void setPlayerOneComplete(boolean playerOneComplete) {
        this.playerOneComplete = playerOneComplete;
    }

    public void setPlayerTwoComplete(boolean playerTwoComplete) {
        this.playerTwoComplete = playerTwoComplete;
    }

    //gets

    public boolean getMiniGame() {
        return nextGame;
    }

    public int getAcertadas() {
        return acertados;
    }


    //Game methods

    public void startClock() {
        start = System.currentTimeMillis();
        acertados = 0;
    }

    public boolean sealGame() {
        if (nextGame){
            //typeracer
            nextGame = !nextGame;
            return acertados == 1; //TODO: condicao para ganhar
        }
        else{
            //mathgame
            if (typeRacerEnabled)
                nextGame = !nextGame;
            return acertados >= 5;
        }
    }

    public long getTime() {
        return start;
    }

    //jogo matematica
    public void criaQuestaoMatematica(){
        numero1 = (int) ((Math.random() * 10) + 1);
        numero2 = (int) ((Math.random() * 10) + 1);
        switch ((int) (Math.random() * 4)){
            case 0:
                operacao = Operacao.SOMA;
                break;
            case 1:
                operacao = Operacao.SUB;
                break;
            case 2:
                operacao = Operacao.MULT;
                break;
            case 3:
                operacao = Operacao.DIV;
                break;
        }
    }

    public Operacao getOperation() {
        return operacao;
    }

    public int getNumber1() {
        return numero1;
    }

    public int getNumber2() {
        return numero2;
    }

    public boolean validaConta(int resposta) {
        switch (operacao){
            case SOMA:
                if (numero1 + numero2 == resposta){
                    acertados++;
                    return true;
                }else
                    return false;
            case SUB:
                if (numero1 - numero2 == resposta){
                    acertados++;
                    return true;
                }else
                    return false;
            case MULT:
                if (numero1 * numero2 == resposta){
                    acertados++;
                    return true;
                }else
                    return false;
            case DIV:
                if (numero1 / numero2 == resposta){
                    acertados++;
                    return true;
                }else
                    return false;
        }
        return false;
    }


    //jogo typeracer

    public String getQuestaoTypeRacer() {
        //esta funcao escolhe e devolve a questao para typeracer, cada vez que é chamada uma nova frase é escolhida
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        List<Integer> numbers = new ArrayList<>();

        do {
            int x = random.nextInt(palavrasLidas - 1);
            if (!numbers.contains(x)) {
                numbers.add(x);
                sb.append(listaPalavras.get(x)).append(" ");
            }
        } while (numbers.size() < 5);

        fraseEscolhidaTypeRacer = sb.toString().trim();

        return fraseEscolhidaTypeRacer;
    }

    public void verificaTypeRacer(String resposta) {
        if (!fraseEscolhidaTypeRacer.equals(resposta)){
            acertados = -1;
        }else  if ((System.currentTimeMillis() - start >= (fraseEscolhidaTypeRacer.length() * 1000L) / 2))
            acertados = 1;
        else
            acertados = 0;
    }
}
