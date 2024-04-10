package com.example.magicfruitsgame;

import com.example.magicfruitsgame.model.Game;
import com.example.magicfruitsgame.model.Symbol;
import com.example.magicfruitsgame.service.ReelsDefinitionService;
import com.example.magicfruitsgame.service.SlotMachineService;
import com.example.magicfruitsgame.service.SymbolService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Optional;

public class SlotMachineUI{

    private static final int REEL_COUNT = 3;
    private static final int SYMBOL_COUNT = 3;

    private SlotMachineService slotMachineService;

    private ImageView[][] reelImageViews;
    private Button startButton;
    private Button payInButton;
    private boolean autoSpinEnabled = false;
    private final Label balanceLabel;

    public SlotMachineUI(Label balanceLabel) {
        this.balanceLabel = balanceLabel;
    }

    public void start(Stage primaryStage) throws Exception {
        Game game = new Game();
        Symbol[] symbols = SymbolService.createSymbols();
        int[][] reelsDefinition = ReelsDefinitionService.createReelsDefinition();

        slotMachineService = new SlotMachineService(game, symbols, reelsDefinition);

        BorderPane root = new BorderPane();
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        reelImageViews = new ImageView[REEL_COUNT][SYMBOL_COUNT];
        for (int i = 0; i < REEL_COUNT; i++) {
            for (int j = 0; j < SYMBOL_COUNT; j++) {
                reelImageViews[i][j] = new ImageView();
                gridPane.add(reelImageViews[i][j], i, j);
            }
        }

        startButton = new Button("START");
        startButton.setOnAction(e -> {
            slotMachineService.play(reelImageViews);
            startButton.setDisable(true);
        });

        payInButton = new Button("PAY IN");
        payInButton.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Pay In");
            dialog.setHeaderText("Enter the amount to deposit:");
            dialog.setContentText("Amount:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                try {
                    int amount = Integer.parseInt(result.get());
                    if (amount > 0) {
                        game.setBalance(game.getBalance() + amount);
                        balanceLabel.setText("Balance: " + game.getBalance()); // Aktualizacja etykiety saldo
                    } else {
                        showAlert("Invalid amount", "Please enter a positive amount.");
                    }
                } catch (NumberFormatException ex) {
                    showAlert("Invalid input", "Please enter a valid number.");
                }
            }
        });

        Button autoSpinButton = new Button("Auto Spin");
        autoSpinButton.setOnAction(e -> {
            autoSpinEnabled = !autoSpinEnabled;
            if (autoSpinEnabled) {
                startAutoSpin();
            }
        });

        Label balanceLabel = new Label("Balance: " + game.getBalance());
        Label stakeLabel = new Label("Stake: " + game.getStake());
        Label lastWinLabel = new Label("Last Win: " + game.getLastWin());

        root.setTop(gridPane);
        root.setLeft(startButton);
        root.setCenter(autoSpinButton);
        root.setRight(payInButton);
        root.setBottom(balanceLabel);
        BorderPane.setMargin(balanceLabel, new Insets(10, 0, 0, 10));
        root.setBottom(stakeLabel);
        BorderPane.setMargin(stakeLabel, new Insets(10, 0, 0, 10));
        root.setBottom(lastWinLabel);
        BorderPane.setMargin(lastWinLabel, new Insets(10, 0, 0, 10));

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Magic Fruits Game");
        primaryStage.show();
    }

    private void startAutoSpin() {
        Timeline autoSpinTimeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
            if (autoSpinEnabled) {
                slotMachineService.play(reelImageViews);
            }
        }));
        autoSpinTimeline.setCycleCount(Timeline.INDEFINITE);
        autoSpinTimeline.play();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
