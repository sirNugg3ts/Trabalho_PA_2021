package pt.isec.a2018019825.jogo.logica;

import pt.isec.a2018019825.jogo.iu.gui.ConstantesGUI;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;

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

    public void jogaPecaDourada(int i) throws IOException {
        me.jogaPecaDourada(i);
        propertyChangeSupport.firePropertyChange(ConstantesGUI.PROPRIEDADE_PLAYPIECE,0,1);
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

    public String obtemTypeRacerQuestao() {
        return me.getQuestaoTypeRacer();
    }

    public void validaQuestaoTypeRacer(String text) {
        me.verificaTypeRacer(text);
        propertyChangeSupport.firePropertyChange(ConstantesGUI.PROPRIEDADE_ENDMINIGAME,0,1);
    }

    public String obtemMathQuestao() {
        return me.getQuestaoMath();
    }

    public void validaQuestaoMath(int parseInt) {
        int status = me.validaConta(parseInt);
        if (status == -1 || status == 2)
            propertyChangeSupport.firePropertyChange(ConstantesGUI.PROPRIEDADE_ENDMINIGAME,0,1);
        else
            propertyChangeSupport.firePropertyChange(ConstantesGUI.RESPONDEMINIGAME,null, status);

    }

    public void startMiniGame() {
        me.startMiniGame();
    }

    public String getAcertadas() {
        return String.valueOf(me.getAcertados());
    }

    public String getPecasDouradas() {
        return   String.valueOf( me.getNPecasDouradas(vezJogador1()));
    }

    public int getIntPecasDouradas() {
        return  me.getNPecasDouradas(vezJogador1());
    }

    public int getRondas(){
        return  me.getRondas();
    }

    public String getRondasBackDisponiveis() {
        return String.valueOf(me.getNoSnapshots());
    }

    public void cancelaVoltarAtras() {
        propertyChangeSupport.firePropertyChange(ConstantesGUI.CANCELA_VOLTARATRAS,null,1);
    }

    public void voltarAtras(int parseInt) throws Exception {
        boolean player = vezJogador1();
        for (int i = 0;i<parseInt;i++)
            me.undo(player);
        propertyChangeSupport.firePropertyChange(ConstantesGUI.VOLTARATRAS,null,1);
    }

    public boolean isTabuleiroCheio() {
        return me.isTabuleiroCheio();
    }

    public String getWinner() {
        return me.getWinner();
    }

    public void newGame() {
        me = new MaquinaEstados();
        propertyChangeSupport.firePropertyChange(ConstantesGUI.COMECANOVOJOGO,null,1);
    }

    public void lerJogo(File selectedFile) throws Exception {
        me.setJogo(selectedFile);
        propertyChangeSupport.firePropertyChange(ConstantesGUI.PROPRIEDADE_CARREGAJOGO,null,1);
    }

    public void gravaJogo(File selectedFile) throws IOException {
        me.saveGame(selectedFile);
    }

    public String tabuleiroToString() {
        return me.tabuleiroToString();
    }
}
