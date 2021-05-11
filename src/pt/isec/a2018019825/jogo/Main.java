package pt.isec.a2018019825.jogo;

import pt.isec.a2018019825.jogo.iu.texto.Connect4IU;
import pt.isec.a2018019825.jogo.logica.MaquinaEstados;

public class Main {
    public static void main(String[] args) {
        MaquinaEstados me = new MaquinaEstados();
        Connect4IU connect4IU = new Connect4IU(me);
        connect4IU.comeca();
    }
}
