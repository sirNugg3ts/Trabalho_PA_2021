package pt.isec.a2018019825.jogo.iu.texto;

import pt.isec.a2018019825.jogo.Utils.Utils;
import pt.isec.a2018019825.jogo.logica.MaquinaEstados;
import pt.isec.a2018019825.jogo.logica.Situacao;

import java.io.IOException;
import java.util.Scanner;

public class Connect4IU {

    MaquinaEstados me;
    boolean sair;

    public Connect4IU(MaquinaEstados me) {
        this.me = me;
        sair = false;
    }

    public void comeca() {
        while (!sair) {
            //menu here
            System.out.println(me);
            Situacao situacao = me.getSituacaoAtual();
            switch (situacao) {
                case AGUARDA_INICIO:
                    comecaIU();
                    break;
                case AGUARDA_JOGADOR1:
                case AGUARDA_JOGADOR2:
                    aguardaJogadorIU();
                    break;
                case AGUARDA_MINIJOGO:
                    AguardaMinijogoIU();
                    break;
                case AGUARDA_INICIO_MINIJOGO:
                    MinijogoIU();
                    break;
                case MIINIJOGO_MATHGAME:
                    MathGameIU();
                    break;
                case MINIJOGO_TYPERACER:
                    TypeRacerIU();
                    break;
                case FIM_JOGO:
                    fimJogoIU();
                    break;
            }
        }
        sairIU();
    }

    private void MathGameIU() {
        int reposta = Utils.pedeInteiro(me.getQuestaoMath());
        switch (me.validaConta(reposta)){
            case -1:
                System.out.println("Tempo terminou!");
                System.out.println("Perdeu o minijogo");
                break;
            case 0:
                System.out.println("Errado!");
                break;
            case 1:
                System.out.println("Certo!");
                break;
            case 2:
                System.out.println("Certo!\nGanhaste o minijogo");
                break;
        }
    }

    private void TypeRacerIU(){
        Scanner sc = new Scanner(System.in);
        System.out.println(me.getQuestaoTypeRacer());
        String resposta = sc.nextLine();
        me.verificaTypeRacer(resposta);
        switch (me.getAcertados()) {
            case -1:
                System.out.println("Reposta errada!");
                break;
            case 0: {
                System.out.println("Reposta certa mas demoraste demasiado tempo");
                break;
            }
            case 1: {
                System.out.println("Reposta certa!");
                break;
            }
        }
    }

    private void MinijogoIU() {

        if (me.getMiniGame())
            System.out.println("Jogo escolhido: typeRacer");
        else {
            System.out.println("Jogo escolhia: MathGame");
        }

        //so para dar tempo a pessoa de ler o nome do jogo

        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        me.startMiniGame();
    }



    private void AguardaMinijogoIU() {
        //me.nextPlayer -> True - Player 1
        //              -> False - Player 2
        if (me.vezJogador1()) {
            System.out.println(me.getPlayerOneName() + ", podes jogar um minijogo para ganhar uma peça dourada");
            int op = Utils.escolheOpcao("Sim", "Não");
            if (op == 1)
                me.miniJogo();
            else
                me.ignoraMiniJogo();
        } else {
            System.out.println(me.getPlayerTwoName() + ", podes jogar um minijogo para ganhar uma peça dourada\n Queres jogar?");
            int op = Utils.escolheOpcao("Sim", "Não");
            if (op == 1)
                me.miniJogo();
            else
                me.ignoraMiniJogo();
        }
    }

    private void fimJogoIU() {

        if (me.isTabuleiroCheio())
            System.out.println("O jogo terminou: Empate");
        else {
            System.out.println("O jogo terminou: " + me.getWinner() + " é o vencedor");
        }

        int op = Utils.escolheOpcao("Iniciar um novo jogo", "Ver log completo", "Ver log deste jogo", "Carregar um jogo", "Sair");
        switch (op) {
            case 1:
                me.termina(false);
                break;
            case 2:
                System.out.println(me.getAllLogs());
                break;
            case 3:
                System.out.println(me.getLogAtual());
                break;
            case 4:
                loadGameIU();
                break;
            case 5:
                me.termina(true);
                sair = true;
                break;
        }
    }

    private void comecaIU() {
        int op = Utils.escolheOpcao("Humano x Humano", "Humano x Bot", "Bot x Bot", "Carregar um jogo", "Sair");

        String nomeJogador1, nomeJogador2;

        switch (op) {
            case 1:
                nomeJogador1 = obtemNomeJogador(0);
                do {
                    nomeJogador2 = obtemNomeJogador(1);
                    if (nomeJogador2.equalsIgnoreCase(nomeJogador1))
                        System.out.println("Não pode ter 2 jogadores com o mesmo nome!");
                } while (nomeJogador2.equalsIgnoreCase(nomeJogador1));
                me.comeca(nomeJogador1, nomeJogador2);
                break;
            case 2:
                nomeJogador1 = obtemNomeJogador(0);
                nomeJogador2 = "";
                me.comeca(nomeJogador1, nomeJogador2);
                break;
            case 3:
                nomeJogador1 = "";
                nomeJogador2 = "";
                me.comeca(nomeJogador1, nomeJogador2);
                break;
            case 4:
                loadGameIU();
                break;
            case 0:
                sair = true;
                break;
        }
    }

    private void sairIU() {
        System.out.println("Obrigado por jogar!");
        System.out.println("Trabalho realizado por: Diogo Pascoal @ ISEC 2020/21");
    }

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

    private void aguardaJogadorIU() {
        if (me.isNextPlayerBot()) {
            try {
                me.playBot();
            } catch (Exception e) {
                System.err.println("Erro ao guardar log do jogo: " + e);
            } finally {
                System.out.println("O bot fez a sua jogada\n\n");
            }
        } else {
            int op;
            if (me.getNPecasDouradas(me.vezJogador1()) > 0) {
                op = Utils.escolheOpcao("Jogar Peça Normal", "Jogar Peça Dourada", "Voltar atrás", "Log atual", "Log completo", "Guardar Jogo", "Carrega Jogo", "Sair");
                switch (op) {
                    case 1: {
                        int coluna;
                        do {
                            coluna = Utils.pedeInteiro("Indique a coluna: ");
                            if (coluna < 0 || coluna > 7)
                                System.out.println("Coluna inválida");
                        } while (coluna < 0 || coluna > 7);

                        try {
                            if (me.jogaPeca(coluna) == -1) {
                                System.out.println("Esta coluna está cheia!");
                            }
                        } catch (Exception e) {
                            System.err.println("Erro ao escrever log: " + e);
                        }
                        break;
                    }
                    case 2:
                        int coluna;
                        do {
                            coluna = Utils.pedeInteiro("Indique a coluna: ");
                        } while (coluna < 0 || coluna > 7);
                        me.jogaPecaDourada(coluna);
                        break;
                    case 3: {
                        goBack();
                    }
                    case 4: {
                        System.out.println("---- Log ----");
                        System.out.println(me.getLogAtual());
                        System.out.println("------------");
                        break;
                    }
                    case 5: {
                        System.out.println("---- Full Log ----");
                        System.out.println(me.getAllLogs());
                        System.out.println("----------------------");
                        break;
                    }
                    case 6: {
                        saveGameIU();
                        break;
                    }
                    case 7: {
                        loadGameIU();
                        break;
                    }
                    case 0:
                        sair = true;
                        break;
                }
            } else {
                op = Utils.escolheOpcao("Jogar Peça Normal", "Voltar atrás", "Log atual", "Log completo", "Guardar Jogo", "Carrega Jogo", "Sair");
                switch (op) {
                    case 1: {
                        int coluna;
                        do {
                            coluna = Utils.pedeInteiro("Indique a coluna: ");
                            if (coluna < 0 || coluna > 7)
                                System.out.println("Coluna inválida");
                        } while (coluna < 0 || coluna > 7);
                        try {
                            me.jogaPeca(coluna);
                        } catch (Exception e) {
                            System.err.println("Erro ao escrever log: " + e);
                        }
                        break;
                    }
                    case 2: {
                        goBack();
                        break;
                    }
                    case 3: {
                        System.out.println("---- Log ----");
                        System.out.println(me.getLogAtual());
                        System.out.println("------------");
                        break;
                    }
                    case 4: {
                        System.out.println("---- Full Log ----");
                        System.out.println(me.getAllLogs());
                        System.out.println("----------------------");
                        break;
                    }
                    case 5: {
                        saveGameIU();
                        break;
                    }
                    case 6: {
                        loadGameIU();
                        break;
                    }
                    case 0:
                        sair = true;
                        break;
                }
            }
        }
    }

    private void saveGameIU() {
        String nome;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("Indique o nome do ficheiro save: ");
            nome = sc.nextLine();
        } while (nome.isEmpty());

        try {
            me.saveGame(nome);
        } catch (IOException e) {
            System.err.println("Erro ao guardar jogo: " + e);
        }
    }

    private void goBack() {
        int nSnaps;
        do {
            nSnaps = Utils.pedeInteiro("Quantas rondas?");
            if (nSnaps > me.getNoSnapshots())
                System.out.println("O número de rondas gravadas é inferior");
            if (nSnaps > me.getCreditos(me.vezJogador1()))
                System.out.println("Não tem créditos suficientes");
        } while (nSnaps > me.getNoSnapshots() || nSnaps > me.getCreditos(me.vezJogador1()));
        for (int i = 0; i < nSnaps; i++) {
            try {
                me.undo();
            } catch (Exception e) {
                System.err.println("Erro ao voltar atras: " + e);
            }
        }
    }

    private void loadGameIU() {
        String nome;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("Indique o nome do ficheiro: ");
            nome = sc.nextLine();
        } while (nome.isEmpty());

        try {
            me.setJogo(nome);
        } catch (Exception e) {
            System.err.println("Erro ao ler ficheiro: " + e);
        }
    }
}
