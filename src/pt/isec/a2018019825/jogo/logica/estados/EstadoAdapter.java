package pt.isec.a2018019825.jogo.logica.estados;

import pt.isec.a2018019825.jogo.logica.dados.Jogo4EmLinha;

public abstract class EstadoAdapter implements IEstado{

    protected Jogo4EmLinha jogo;

    public EstadoAdapter(Jogo4EmLinha jogo) {
        this.jogo = jogo;
    }

    @Override
    public IEstado comeca() {
        return this;
    }

    @Override
    public IEstado termina() {
        return this;
    }

    @Override
    public IEstado jogaPeca(int coluna) {
        return this;
    }

    @Override
    public IEstado minijogo() {
        return this;
    }
}
