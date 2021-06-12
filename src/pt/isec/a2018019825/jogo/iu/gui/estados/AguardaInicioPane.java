package pt.isec.a2018019825.jogo.iu.gui.estados;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.a2018019825.jogo.iu.gui.ConstantesGUI;
import pt.isec.a2018019825.jogo.logica.JogoObservavel;

public class AguardaInicioPane extends VBox {
    private HBox buttonsBar;
    private HBox namesBar;
    private JogoObservavel jogoObservavel;

    private ToggleGroup radioGroup;
    RadioButton humanoVShumano,humanoVSbot,botVSbot;
    Button iniciarButton;

    Label playerOneLabel,playerTwoLabel;
    TextField playerOneName,playerTwoName;



    public AguardaInicioPane(JogoObservavel jogoObservavel){
        this.jogoObservavel = jogoObservavel;
        criarVista();
        RegistaListeners();
    }


    private void RegistaListeners() {
        humanoVShumano.setOnAction((e) -> {
            iniciarButton.setDisable(false);
            playerOneName.setDisable(false);
            playerOneName.setEditable(true);
            playerTwoName.setDisable(false);
            playerTwoName.setEditable(true);
            playerOneName.setText("Player One");
            playerTwoName.setText("Player Two");
        });

        humanoVSbot.setOnAction((e) -> {
            iniciarButton.setDisable(false);
            playerOneName.setDisable(false);
            playerOneName.setEditable(true);
            playerTwoName.setEditable(false);
            playerTwoName.setDisable(true);
            playerTwoName.setText("Bot1");
        });

        botVSbot.setOnAction((e)->{
            iniciarButton.setDisable(false);
            playerTwoName.setEditable(false);
            playerTwoName.setDisable(true);
            playerOneName.setEditable(false);
            playerOneName.setDisable(true);
            playerOneName.setText("Bot1");
            playerTwoName.setText("Bot2");
        });

        iniciarButton.setOnAction((e) -> {
            if (humanoVSbot.isSelected())
                jogoObservavel.comeca(playerOneName.getText(),"");
            else if (botVSbot.isSelected())
                jogoObservavel.comeca("","");
            else
                jogoObservavel.comeca(playerOneName.getText(),playerTwoName.getText());
        });


    }

    private void criarVista() {
        HBox playerOneBox,playerTwoBox;

        buttonsBar = new HBox();
        namesBar = new HBox();
        radioGroup = new ToggleGroup();

        humanoVShumano = new RadioButton("Humano x Humano");
        humanoVSbot = new RadioButton("Humano x Bot");
        botVSbot = new RadioButton("Bot x Bot");
        iniciarButton = new Button("Começar");
        playerOneLabel = new Label("Jogador 1: ");
        playerTwoLabel = new Label("Jogador 2: ");
        playerOneName = new TextField();
        playerTwoName = new TextField();
        playerOneBox = new HBox();
        playerTwoBox = new HBox();



        radioGroup.getToggles().addAll(humanoVShumano,humanoVSbot,botVSbot);
        buttonsBar.getChildren().addAll(humanoVShumano,humanoVSbot,botVSbot);
        playerOneBox.getChildren().addAll(playerOneLabel,playerOneName);
        playerTwoBox.getChildren().addAll(playerTwoLabel,playerTwoName);
        namesBar.getChildren().addAll(playerOneBox,playerTwoBox);
        this.getChildren().addAll(namesBar,buttonsBar,iniciarButton);


        iniciarButton.setDisable(true);
        playerOneName.setEditable(false);
        playerOneName.setDisable(true);
        playerTwoName.setEditable(false);
        playerTwoName.setDisable(true);



        playerOneName.setText("Player One");
        playerTwoName.setText("Player Two");




        playerOneLabel.setAlignment(Pos.CENTER);
        playerTwoLabel.setAlignment(Pos.CENTER);
        playerOneName.setAlignment(Pos.CENTER);
        playerTwoName.setAlignment(Pos.CENTER);

        buttonsBar.setPadding(new Insets(10));
        namesBar.setPadding(new Insets(10));
        buttonsBar.setSpacing(10);
        namesBar.setSpacing(10);
        buttonsBar.setAlignment(Pos.CENTER);
        namesBar.setAlignment(Pos.CENTER);



        this.setAlignment(Pos.CENTER);
        this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,null,new BorderWidths(2))));
        this.setPadding(new Insets(10));












    }
}
