package com.example.magicfruitsgame.controller;

import com.example.magicfruitsgame.model.Symbol;
import com.example.magicfruitsgame.service.GameService;
import com.example.magicfruitsgame.service.SymbolService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Optional;

public class SlotMachineController {
    private final GameService gameService;

    @FXML
    private SymbolService symbolService;

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
        symbolService = new SymbolService();
        symbolService.setSymbolsDirectory(new Image(getClass().getResourceAsStream("/symbols")));


        Image startImage = new Image(getClass().getResourceAsStream("/views/button_start_normal.png"));
        ImageView startImageView = new ImageView(startImage);
        startButton.setGraphic(startImageView);

        Image payInImage = new Image(getClass().getResourceAsStream("/views/button_payin_normal.png"));
        ImageView payInImageView = new ImageView(payInImage);
        payInButton.setGraphic(payInImageView);
    }

    @FXML
    private void handleStartButton() {
        if (!gameService.isGameRunning()) {
            // Rozpoczęcie gry
            gameService.startGame();

            // Odejmowanie stawki od salda
            try {
                gameService.deduct(); // Odejmowanie stawki od salda
            } catch (IllegalArgumentException e) {
                // Obsługa przypadku braku wystarczających środków na koncie
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Insufficient Funds");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText(e.getMessage());
                errorAlert.showAndWait();
                return; // Zakończ metodę w przypadku braku wystarczających środków
            }

            // Przekręcenie bębnów
            gameService.spinBoard();

            // Aktualizacja etykiet
            updateLabels();

            // Wyświetlenie komunikatu o wygranej, jeśli jest
            if (gameService.getLastWin() > 0) {
                Alert winAlert = new Alert(Alert.AlertType.INFORMATION);
                winAlert.setTitle("Congratulations!");
                winAlert.setHeaderText(null);
                winAlert.setContentText("You won: " + gameService.getLastWin());
                winAlert.showAndWait();
            }

            // Sprawdzenie, czy gra została zakończona
            if (!gameService.isGameRunning()) {
                Alert endGameAlert = new Alert(Alert.AlertType.INFORMATION);
                endGameAlert.setTitle("Game Over");
                endGameAlert.setHeaderText(null);
                endGameAlert.setContentText("Game has ended. Your final balance: " + gameService.getBalance());
                endGameAlert.showAndWait();
            }
        } else {
            // Wyświetlenie komunikatu o błędzie, jeśli gra jest już uruchomiona
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Game is already running.");
            alert.showAndWait();
        }
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
        Symbol[][] board = gameService.getBoard();

        balanceLabel.setText(Integer.toString(gameService.getBalance()));
        stakeLabel.setText(Integer.toString(gameService.getStake()));
        lastWinLabel.setText(Integer.toString(gameService.getLastWin()));
    }


}
