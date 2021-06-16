package pt.isec.a2018019825.jogo.logica.estados;

import pt.isec.a2018019825.jogo.logica.Situacao;
import pt.isec.a2018019825.jogo.logica.dados.Jogo4EmLinha;

public class FimJogo extends EstadoAdapter {

    public FimJogo(Jogo4EmLinha jogo) {
        super(jogo);
    }

    @Override
    public IEstado termina(boolean end) {
        if (!end)
            return new AguardaInicio(jogo);
        return null;
    }

    @Override
    public Situacao getSituacaoAtual() {
        return Situacao.FIM_JOGO;
    }
}
