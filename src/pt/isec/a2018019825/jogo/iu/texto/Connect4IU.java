package pt.isec.a2018019825.jogo.iu.texto;

import pt.isec.a2018019825.jogo.Utils.Utils;
import pt.isec.a2018019825.jogo.logica.MaquinaEstados;
import pt.isec.a2018019825.jogo.logica.Situacao;

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
            System.out.println(me.getRounds());
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
        if (me.nextPlayerOne()) {
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
        System.out.println("O jogo terminou");
        if (me.getWinner().equals("Empate"))
            System.out.println("O jogo acabou em empate");
        else
            System.out.println(me.getWinner() + " é o vencedor da partida");

        System.out.println("Novo jogo?");
        int op = Utils.escolheOpcao("Sim", "Não");
        switch (op) {
            case 1:
                me.comeca();
                break;
            default:
                sair = true;
                break;
        }
    }

    private void aguardaJogadorIU() {
        if (me.isNextPlayerBot())
            me.playBot();
        else {
            int op;
            if (me.getNPecasDouradas(me.nextPlayerOne()) > 0) {
                op = Utils.escolheOpcao("Jogar Peça Normal", "Jogar Peça Dourada", "Sair");
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
                    case 0:
                        sair = true;
                        break;
                }
            } else {
                op = Utils.escolheOpcao("Jogar Peça Normal", "Sair");
                switch (op) {
                    case 1: {
                        int coluna;
                        do {
                            coluna = Utils.pedeInteiro("Indique a coluna: ");
                        } while (coluna < 0 || coluna > 7);
                        me.jogaPeca(coluna);
                        break;
                    }
                    case 0:
                        sair = true;
                        break;
                }
            }
        }
    }
}
