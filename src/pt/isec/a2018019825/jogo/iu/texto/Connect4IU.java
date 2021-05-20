package pt.isec.a2018019825.jogo.iu.texto;

import pt.isec.a2018019825.jogo.Utils.Utils;
import pt.isec.a2018019825.jogo.logica.MaquinaEstados;
import pt.isec.a2018019825.jogo.logica.Situacao;

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
            System.out.println("Rondas no: " + me.getRounds());
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
                    minijogoIU();
                    break;
                case FIM_JOGO:
                    fimJogoIU();
                    break;
            }
        }
    }

    private void minijogoIU() {
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

    private void comecaIU() {
        me.comeca();
    }

    private void fimJogoIU() {
        System.out.println(me.tabuleiroToString());
        if (me.termina())
            sair = true;
    }

    private void aguardaJogadorIU() {
        if (me.isNextPlayerBot()){
            me.playBot();
            System.out.println("o bot jogou");
        }

        else {
            int op;
            if (me.getNPecasDouradas(me.vezJogador1()) > 0) {
                op = Utils.escolheOpcao("Jogar Peça Normal", "Jogar Peça Dourada", "Voltar atrás","Log atual","Log completo","Guardar Jogo","Carrega Jogo","Sair");
                switch (op) {
                    case 1: {
                        int coluna;
                        do {
                            coluna = Utils.pedeInteiro("Indique a coluna: ");
                        } while (coluna < 0 || coluna > 7);
                        me.jogaPeca(coluna);
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
                    case 4:{
                        System.out.println(me.getLogAtual());
                        break;
                    }
                    case 5:{
                        System.out.println(me.getAllLogs());
                        break;
                    }
                    case 6:{
                        saveGameIU();
                        break;
                    }
                    case 7:{
                        loadGameIU();
                        break;
                    }
                    case 0:
                        sair = true;
                        break;
                }
            } else {
                op = Utils.escolheOpcao("Jogar Peça Normal","Voltar atras","Log atual","Log completo","Guardar Jogo","Carrega Jogo", "Sair");
                switch (op) {
                    case 1: {
                        int coluna;
                        do {
                            coluna = Utils.pedeInteiro("Indique a coluna: ");
                            if(coluna < 0 || coluna > 7)
                                System.out.println("Coluna inválida");
                        } while (coluna < 0 || coluna > 7);
                        me.jogaPeca(coluna);
                        break;
                    }
                    case 2: {
                        goBack();
                        break;
                    }
                    case 3:{
                        System.out.println("---- Log ----");
                        System.out.println(me.getLogAtual());
                        break;
                    }
                    case 4:{
                        System.out.println("---- Full Log ----");
                        System.out.println(me.getAllLogs());
                        break;
                    }
                    case 5:{
                        saveGameIU();
                        break;
                    }
                    case 6:{
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

        do{
            System.out.println("Indique o nome do ficheiro save: ");
            nome = sc.nextLine();
        }while(nome.isEmpty());

        me.saveGame(nome);
    }

    private void goBack() {
        int nSnaps;
        do {
            nSnaps = Utils.pedeInteiro("Quantas rondas?");
            if (nSnaps > me.getNoSnapshots())
                System.out.println("O número de rondas gravadas é inferior");
            if (nSnaps > me.getCreditos(me.vezJogador1()))
                System.out.println("Não tem créditos suficientes");
        }while (nSnaps > me.getNoSnapshots() || nSnaps > me.getCreditos(me.vezJogador1()));
        for (int i=0;i < nSnaps;i++)
            me.undo();
    }

    private void loadGameIU(){
        String nome;
        Scanner sc = new Scanner(System.in);
        do{
            System.out.println("Indique o nome do ficheiro: ");
            nome = sc.nextLine();
        }while (nome.isEmpty());

        me.setJogo(nome);

    }
}
