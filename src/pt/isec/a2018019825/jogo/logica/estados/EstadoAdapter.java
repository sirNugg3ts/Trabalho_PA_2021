package pt.isec.a2018019825.jogo.logica.estados;

import pt.isec.a2018019825.jogo.logica.dados.Jogo4EmLinha;

public abstract class EstadoAdapter implements IEstado {

    protected Jogo4EmLinha jogo;

    public EstadoAdapter(Jogo4EmLinha jogo) {
        this.jogo = jogo;
    }

    @Override
    public IEstado comeca(String nomeJogador1, String nomeJogador2) {
        return this;
    }

    @Override
    public IEstado termina(boolean restart) {
        return this;
    }

    @Override
    public IEstado jogaPeca(int coluna) throws Exception {
        return this;
    }

    @Override
    public IEstado startMiniGame() {
        return this;
    }

    @Override
    public IEstado recebeResposta(int resposta) {
        return this;
    }

    @Override
    public IEstado recebeResposta(String resposta) {
        return this;
    }

    @Override
    public IEstado terminaMiniJogo() {
        return this;
    }

    @Override
    public IEstado minijogo() {
        return this;
    }

    @Override
    public IEstado jogaPecaDourada(int coluna) {
        return this;
    }

    @Override
    public IEstado ignoraMiniJogo() {
        return this;
    }

    @Override
    public IEstado playBot() throws Exception {
        return this;
    }

    @Override
    public IEstado replay() {
        return this;
    }
}
