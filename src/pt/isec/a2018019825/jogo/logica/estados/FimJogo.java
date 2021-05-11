package pt.isec.a2018019825.jogo.logica.estados;

import pt.isec.a2018019825.jogo.Utils.Utils;
import pt.isec.a2018019825.jogo.logica.Situacao;
import pt.isec.a2018019825.jogo.logica.dados.Jogo4EmLinha;

public class FimJogo extends EstadoAdapter {

    public FimJogo(Jogo4EmLinha jogo) {
        super(jogo);
    }

    @Override
    public IEstado termina() {
         if (jogo.tabuleiroCheio()){
             System.out.println("Empate");
         }else{
             System.out.println(jogo.getWinnerName() + "Venceu a partida");
         }

        System.out.println("Deseja iniciar um novo jogo?");
         int op = Utils.escolheOpcao("Sim","Não");
         switch (op){
             case 1:
                 return new AguardaInicio(jogo);
             default:
                 return null;
         }
    }

    @Override
    public IEstado comeca() {
        return new AguardaInicio(jogo);
    }

    @Override
    public Situacao getSituacaoAtual() {
        return Situacao.FIM_JOGO;
    }
}
