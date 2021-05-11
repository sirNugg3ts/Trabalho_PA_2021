package pt.isec.a2018019825.jogo.iu.texto;

import pt.isec.a2018019825.jogo.Utils.Utils;
import pt.isec.a2018019825.jogo.logica.MaquinaEstados;
import pt.isec.a2018019825.jogo.logica.Situacao;

public class Connect4IU {
    MaquinaEstados me;
    boolean sair;

    public Connect4IU(MaquinaEstados me){
        this.me = me;
        sair = false;
    }

    public void comeca(){
        while(!sair){
            //menu here
            System.out.println(me);
            Situacao situacao = me.getSituacaoAtual();
            switch (situacao) {
                case AGUARDA_INICIO -> comecaIU();
                case AGUARDA_JOGADOR1, AGUARDA_JOGADOR2 -> aguardaJogadorIU();
                case FIM_JOGO -> fimJogoIU();
            }
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
        int op = Utils.escolheOpcao("Sim","Não");
        switch (op){
            case 1:
                me.comeca();
                break;
            default:
                sair = true;
                break;
        }
    }

    private void aguardaJogadorIU(){
        int op = Utils.escolheOpcao("Jogar Peça","Sair");
        switch (op) {
            case 1:
                int coluna ;
                do{
                    coluna = Utils.pedeInteiro("Indique a coluna: ");
                }while (coluna < 0 || coluna > 7);
                me.jogaPeca(coluna);
                break;
            default:
                sair = true;
                break;
        }
    }
}
