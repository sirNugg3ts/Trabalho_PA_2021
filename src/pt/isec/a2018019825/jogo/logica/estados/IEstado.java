package pt.isec.a2018019825.jogo.logica.estados;

import pt.isec.a2018019825.jogo.logica.Situacao;

public interface IEstado {

    IEstado comeca();
    IEstado jogaPeca(int coluna);
    IEstado termina();
    IEstado minijogo();

    Situacao getSituacaoAtual();
}
