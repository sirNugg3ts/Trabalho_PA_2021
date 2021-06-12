package pt.isec.a2018019825.jogo.logica;

import pt.isec.a2018019825.jogo.iu.gui.ConstantesGUI;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class JogoObservavel {
    private MaquinaEstados me;
    private final PropertyChangeSupport propertyChangeSupport;

    public JogoObservavel(MaquinaEstados me) {
        this.me = me;
        propertyChangeSupport = new PropertyChangeSupport(me);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener){
        propertyChangeSupport.addPropertyChangeListener(propertyName,listener);
    }

    public void comeca(String playerOneName,String playerTwoName) {
        me.comeca(playerOneName, playerTwoName);
        propertyChangeSupport.firePropertyChange(ConstantesGUI.PROPRIEDADE_JOGO,0,1);

    }

    public void jogaPeca(int coluna) throws Exception{
        me.jogaPeca(coluna);
        propertyChangeSupport.firePropertyChange(ConstantesGUI.PROPRIEDADE_PLAYPIECE,0,1);
    }

    public void jogaPecaDourada(int i) {
        me.jogaPecaDourada(i);
        propertyChangeSupport.firePropertyChange(ConstantesGUI.PROPRIEDADE_PLAYPIECE,0,i);
    }

    public void playBot() throws Exception {
        me.playBot();
        propertyChangeSupport.firePropertyChange(ConstantesGUI.PROPRIEDADE_PLAYPIECE,0,1);
    }

    public String getPlayerOneName() {
        return me.getPlayerOneName();
    }

    public String getActualPlayerCreditos() {
        return Integer.toString(me.getCreditos(vezJogador1()));
    }

    public boolean vezJogador1() {
        return me.vezJogador1();
    }

    public String getPlayerTwoName() {
        return me.getPlayerTwoName();
    }

    public void cancelaJogada() {
        propertyChangeSupport.firePropertyChange(ConstantesGUI.CANCELA_JOGADA,0,1);
    }


    public boolean colunaCheia(int i) {
        return me.colunaCheia(i);
    }

    public char[][] getTabuleiro() {
        return me.getTabuleiro();
    }

    public Situacao getSituacao() {
        return me.getSituacaoAtual();
    }

    public boolean isNextPlayerBot(){
        return me.isNextPlayerBot();
    }


    public void jogaMiniJogo() {
        me.miniJogo();
        propertyChangeSupport.firePropertyChange(ConstantesGUI.PROPRIEDADE_CHOOSEMINIGAME,0,1);
    }

    public void ignoraMiniJogo(){
        me.ignoraMiniJogo();
        propertyChangeSupport.firePropertyChange(ConstantesGUI.PROPRIEDADE_CHOOSEMINIGAME,0,-1);
    }
}
