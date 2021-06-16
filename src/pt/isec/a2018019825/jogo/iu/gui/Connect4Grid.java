package pt.isec.a2018019825.jogo.iu.gui;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import pt.isec.a2018019825.jogo.iu.gui.resources.ImageLoader;
import pt.isec.a2018019825.jogo.logica.JogoObservavel;

public class Connect4Grid extends GridPane {

    private static final int TILE_SIZE = 80;
    private static final int COLUMNS = 7;
    private static final int ROWS = 6;

    private final Disc[][] grid = new Disc[COLUMNS][ROWS];

    private final JogoObservavel jogoObservavel;

    public Connect4Grid(JogoObservavel jogoObservavel){
        this.jogoObservavel = jogoObservavel;
        criarVista();
        registarObservador();
        atualiza();
    }

    private void criarVista() {
        setVgap(5);
        setHgap(5);
        setPadding(new Insets(10,20,20,20));
        setId("tabuleiro_grid");
    }

    private void registarObservador() {
        jogoObservavel.addPropertyChangeListener(ConstantesGUI.PROPRIEDADE_PLAYPIECE, (e) -> {
            atualiza();
        });
        jogoObservavel.addPropertyChangeListener(ConstantesGUI.VOLTARATRAS,evt -> atualiza());
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
    }

    private static class Disc extends Circle{
        private final boolean red;
        public Disc(boolean red){
            super(TILE_SIZE / 2);
            this.red = red;
            if (red){
                if (ConstantesGUI.SpecialMode){
                    setFill(new ImagePattern(ImageLoader.getImage("flag1.png")));
                }else
                    setFill(Color.YELLOW);
            }
            else{
                if (ConstantesGUI.SpecialMode)
                    setFill(new ImagePattern(ImageLoader.getImage("flag2.png")));
                else
                    setFill(Color.RED);
            }

            setCenterX(TILE_SIZE / 2);
            setCenterY(TILE_SIZE / 2);
        }
        public Disc(int transparent){
            super(TILE_SIZE /2, Color.WHITE);
            this.red = false;

            setCenterX(TILE_SIZE /2);
            setCenterY(TILE_SIZE /2);
        }
    }
}


