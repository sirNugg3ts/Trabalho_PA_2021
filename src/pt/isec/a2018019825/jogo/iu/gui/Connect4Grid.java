package pt.isec.a2018019825.jogo.iu.gui;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import pt.isec.a2018019825.jogo.logica.JogoObservavel;

public class Connect4Grid extends GridPane {

    private static final int TILE_SIZE = 80;
    private static final int COLUMNS = 7;
    private static final int ROWS = 6;

    private Disc[][] grid = new Disc[COLUMNS][ROWS];

    private JogoObservavel jogoObservavel;

    public Connect4Grid(JogoObservavel jogoObservavel){
        this.jogoObservavel = jogoObservavel;
        criarVista();
        registarObservador();
        atualiza();
    }


    private void criarVista() {
        setHgap(5);
        setVgap(5);
        this.setMinWidth(TILE_SIZE*(grid.length+1));
        this.setMinHeight(TILE_SIZE*(grid[0].length+1));
        this.setMaxWidth(TILE_SIZE*(grid.length+1));
        this.setMaxHeight(TILE_SIZE*(grid[0].length+1));
        setBorder(new Border(new BorderStroke(Color.DEEPSKYBLUE, BorderStrokeStyle.DOTTED,new CornerRadii(50),new BorderWidths(2))));
        setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE,new CornerRadii(50),null)));


    }

    private void registarObservador() {
        jogoObservavel.addPropertyChangeListener(ConstantesGUI.PROPRIEDADE_PLAYPIECE, (e) -> {
            atualiza();
        });
        jogoObservavel.addPropertyChangeListener(ConstantesGUI.VOLTARATRAS,evt -> atualiza());
        jogoObservavel.addPropertyChangeListener(ConstantesGUI.REPLAY,evt -> atualiza());

    }


    public void atualiza(){
        char[][] tabuleiro = jogoObservavel.getTabuleiro();

        for (int x = 0; x < tabuleiro.length; x++) {
        for (int y = 0; y < tabuleiro[0].length;y++){

                if (tabuleiro[x][y] != ' '){
                    if (tabuleiro[x][y] == 'R')
                        grid[x][y] = new Disc(true);
                    else
                        grid[x][y] = new Disc(false);
                }else
                    grid[x][y] = new Disc(1);
                this.add(grid[x][y],x,(tabuleiro.length)-y);
            }
        }
        this.setPadding(new Insets(10));
    }

    private static class Disc extends Circle{
        private final boolean red;
        public Disc(boolean red){
            super(TILE_SIZE / 2, red ? Color.RED : Color.YELLOW);
            this.red = red;

            setCenterX(TILE_SIZE / 2);
            setCenterY(TILE_SIZE / 2);
        }
        public Disc(int transparent){
            super(TILE_SIZE / 2, Color.WHITE);
            this.red = false;

            setCenterX(TILE_SIZE / 2);
            setCenterY(TILE_SIZE / 2);
        }
    }
}


