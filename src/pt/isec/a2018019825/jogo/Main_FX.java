package pt.isec.a2018019825.jogo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.isec.a2018019825.jogo.iu.gui.Connect4IU_Grafico;
import pt.isec.a2018019825.jogo.logica.JogoObservavel;
import pt.isec.a2018019825.jogo.logica.MaquinaEstados;

public class Main_FX extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        MaquinaEstados me = new MaquinaEstados();
        JogoObservavel obs = new JogoObservavel(me);

        stage.setTitle("Jogo 4 Em Linha - Diogo Pascoal");
        Connect4IU_Grafico connect4IU_grafico = new Connect4IU_Grafico(obs);
        Scene scene = new Scene(connect4IU_grafico,900,900);
        stage.setScene(scene);
        stage.setOnCloseRequest(windowEvent -> Platform.exit());
        stage.show();

    }
}
