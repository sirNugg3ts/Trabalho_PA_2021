package pt.isec.a2018019825.jogo.logica.estados;

import pt.isec.a2018019825.jogo.logica.Situacao;

public interface IEstado {

    IEstado comeca(String nomeJogador1, String nomeJogador2);

    IEstado jogaPeca(int coluna) throws Exception;

    IEstado termina(boolean restart);

    IEstado minijogo();

    IEstado jogaPecaDourada(int coluna);

    IEstado playBot() throws Exception;

    IEstado acabaMiniJogo();

    Situacao getSituacaoAtual();

    IEstado ignoraMiniJogo();
}
