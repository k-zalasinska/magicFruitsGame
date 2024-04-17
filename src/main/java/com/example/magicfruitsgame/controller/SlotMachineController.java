package com.example.magicfruitsgame.controller;

import com.example.magicfruitsgame.service.GameService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;
import java.util.Optional;

public class SlotMachineController {
    private final GameService gameService;

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
        Image startImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/views/button_start_normal.png")));
        ImageView startImageView = new ImageView(startImage);
        startButton.setGraphic(startImageView);

        Image payInImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/views/button_payin_normal.png")));
        ImageView payInImageView = new ImageView(payInImage);
        payInButton.setGraphic(payInImageView);
    }

    @FXML
    private void handleStartButton() {
        gameService.startGame();
        try {
            gameService.deduct();
        } catch (IllegalArgumentException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Insufficient Funds");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
            return;
        }
        gameService.spinBoard();
        updateLabels();

        if (gameService.getLastWin() > 0) {
            Alert winAlert = new Alert(Alert.AlertType.INFORMATION);
            winAlert.setTitle("Congratulations!");
            winAlert.setHeaderText(null);
            winAlert.setContentText("You won: " + gameService.getLastWin());
            winAlert.showAndWait();
        }
    }
//
//        //sprawdz czy gra została zakończ
//        if (!game.isGameRunning()) {
//            Alert endGameAlert = new Alert(Alert.AlertType.INFORMATION);
//            endGameAlert.setTitle("Game Over");
//            endGameAlert.setHeaderText(null);
//            endGameAlert.setContentText("Game has ended. Your final balance: " + game.getBalance());
//            endGameAlert.showAndWait();
//        }
//    } else
//
//    {
//        //wyświetl komuo błędzie, jeśli gra jest już uruchom
//        Alert alert = new Alert(Alert.AlertType.WARNING);
//        alert.setTitle("Warning");
//        alert.setHeaderText(null);
//        alert.setContentText("Game is already running.");
//        alert.showAndWait();
//    }


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
                updateLabels();
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Deposit Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Balance has been successfully deposited.");
                successAlert.showAndWait();
            } catch (NumberFormatException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Invalid Amount");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Please enter a valid integer amount.");
                errorAlert.showAndWait();
            } catch (IllegalArgumentException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Invalid Amount");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText(e.getMessage());
                errorAlert.showAndWait();
            }
        });
    }

    private void updateLabels() {
        balanceLabel.setText(Integer.toString(gameService.getBalance()));
        stakeLabel.setText(Integer.toString(gameService.getStake()));
        lastWinLabel.setText(Integer.toString(gameService.getLastWin()));
    }


}
