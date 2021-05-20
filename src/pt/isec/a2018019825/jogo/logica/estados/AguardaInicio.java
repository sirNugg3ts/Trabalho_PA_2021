package pt.isec.a2018019825.jogo.logica.estados;

import pt.isec.a2018019825.jogo.logica.Situacao;
import pt.isec.a2018019825.jogo.logica.dados.Jogo4EmLinha;

public class AguardaInicio extends EstadoAdapter {

    public AguardaInicio(Jogo4EmLinha jogo) {
        super(jogo);
    }

    @Override
    public IEstado comeca() {
        jogo.comeca();
        jogo.recorda("Vou iniciar o jogo");
        return new AguardaJogador(jogo);
    }

    @Override
    public Situacao getSituacaoAtual() {
        return Situacao.AGUARDA_INICIO;
    }
}
