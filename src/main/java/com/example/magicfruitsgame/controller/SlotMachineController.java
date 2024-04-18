package com.example.magicfruitsgame.controller;

import com.example.magicfruitsgame.model.Symbol;
import com.example.magicfruitsgame.service.GameService;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.Objects;
import java.util.Optional;

public class SlotMachineController {

    private final GameService gameService;
    private int lastWin;

    @FXML
    private GridPane reelsGrid;

    @FXML
    private Label balanceLabel;

    @FXML
    private Label stakeLabel;

    @FXML
    private Label lastWinLabel;

    @FXML
    private Button startButton;

    @FXML
    private Button payInButton;


    public SlotMachineController(GameService gameService) {
        this.gameService = gameService;
    }

    @FXML
    public void initialize() {
        setImageOnButton(startButton, "/views/button_start_normal.png");
        setImageOnButton(payInButton, "/views/button_payin_normal.png");

        initializeReelsGrid();
    }

    @FXML
    private void handleStartButton() {
        try {
            gameService.deduct();
        } catch (IllegalArgumentException e) {
            showErrorAlert("Insufficient Funds", e.getMessage());
            return;
        }

        reelsGrid.getChildren().clear();
        reelsGrid = gameService.createReelsGrid();

        Symbol[][] symbols = gameService.spinBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Symbol symbol = symbols[i][j];
                if (symbol != null) {
                    ImageView symbolImageView = gameService.createSymbolImageView(symbol);
                    reelsGrid.add(symbolImageView, j, i);
                    symbolImageView.toFront();
                }
            }
        }

        if (gameService.checkWin(symbols)) {
            lastWin = gameService.calculateWin(symbols[1][0].id());
            gameService.updateBalance(lastWin);
            showInfoAlert("Congratulations!", "You won: " + lastWin);
        }

        updateLabels();
    }

    @FXML
    private void handlePayInButton() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Deposit");
        dialog.setHeaderText("Enter the amount you want to deposit:");
        dialog.setContentText("Amount:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(amount -> {
            try {
                int depositAmount = Integer.parseInt(amount);
                gameService.deposit(depositAmount);
                showInfoAlert("Deposit Success", "Balance has been successfully deposited.");
            } catch (NumberFormatException e) {
                showErrorAlert("Invalid Amount", "Please enter a valid integer amount.");
            } catch (IllegalArgumentException e) {
                showErrorAlert("Invalid Amount", e.getMessage());
            }
        });
        updateLabels();
    }


    private void updateLabels() {
        balanceLabel.setText(Integer.toString(gameService.getBalance()));
        stakeLabel.setText(Integer.toString(gameService.getStake()));
        lastWinLabel.setText(Integer.toString(lastWin));
    }

    private void initializeReelsGrid() {
        //wymiary siatki symb
        reelsGrid.setPrefWidth(300);
        reelsGrid.setPrefHeight(200);
        reelsGrid.setMaxWidth(Double.MAX_VALUE);
        reelsGrid.setMaxHeight(Double.MAX_VALUE);

        //ustawia siatkę symb na środku rodzica- background
        reelsGrid.setAlignment(Pos.CENTER);
    }

    private void setImageOnButton(Button button, String imagePath) {
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath))));
        button.setGraphic(imageView);
    }

    private void showErrorAlert(String title, String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    private void showInfoAlert(String title, String message) {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle(title);
        infoAlert.setHeaderText(null);
        infoAlert.setContentText(message);
        infoAlert.showAndWait();
    }
}
