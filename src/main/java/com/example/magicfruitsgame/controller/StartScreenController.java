package com.example.magicfruitsgame.controller;

import com.example.magicfruitsgame.service.GameService;
import com.example.magicfruitsgame.service.PlayerService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;
import java.util.Optional;

public class StartScreenController {
    @FXML
    private ImageView backgroundImageView;

    @FXML
    private Label balanceLabel;

    @FXML
    private Button buttonStart;

    @FXML
    private Button buttonPayIn;

    private final GameService gameService;
    private final PlayerService playerService;

    public StartScreenController(GameService gameService, PlayerService playerService) {
        this.gameService = gameService;
        this.playerService = playerService;
    }

    @FXML
    public void initialize() {
        // Inicjalizacja stanu gry
        updateBalance();

        // Ustawienie obrazka tła
        Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/magicfruitsgame/images/background.jpg")));
        backgroundImageView.setImage(backgroundImage);

        // Ustawienie obrazków przycisków
        Image buttonStartNormalImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/magicfruitsgame/images/button_start_normal.png")));
        buttonStart.setGraphic(new ImageView(buttonStartNormalImage));

        Image buttonPayInNormalImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/magicfruitsgame/images/button_payin_normal.png")));
        buttonPayIn.setGraphic(new ImageView(buttonPayInNormalImage));

        // Dodanie obsługi zdarzeń dla przycisków
        setButtonEvents();
    }

    private void updateBalance() {
        int balance = playerService.getBalance();
        balanceLabel.setText("Balance: " + balance);
    }

    @FXML
    private void startNewRound() {
        gameService.startNewRound();
        updateBalance();
    }

    @FXML
    private void handleRefillAccount() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Account Refill");
        dialog.setHeaderText("Enter the amount to refill your account:");
        dialog.setContentText("Amount:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(amount -> {
            try {
                int refillAmount = Integer.parseInt(amount);
                playerService.deposit(refillAmount);
                updateBalance();
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Please enter a valid integer value.");
            } catch (IllegalArgumentException e) {
                System.out.println("The top-up amount must be greater than zero.");
            }
        });
    }


    private void setButtonEvents() {
        buttonStart.setOnMouseEntered(event -> {
            Image buttonStartOnHoverImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/magicfruitsgame/images/button_start_onhover.png")));
            buttonStart.setGraphic(new ImageView(buttonStartOnHoverImage));
        });

        buttonStart.setOnMouseExited(event -> {
            Image buttonStartNormalImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/magicfruitsgame/images/button_start_normal.png")));
            buttonStart.setGraphic(new ImageView(buttonStartNormalImage));
        });

        buttonStart.setOnMousePressed(event -> {
            Image buttonStartOnPressImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/magicfruitsgame/images/button_start_onpress.png")));
            buttonStart.setGraphic(new ImageView(buttonStartOnPressImage));
        });

        buttonPayIn.setOnMouseEntered(event -> {
            Image buttonPayInOnHoverImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/magicfruitsgame/images/button_payin_onhover.png")));
            buttonPayIn.setGraphic(new ImageView(buttonPayInOnHoverImage));
        });

        buttonPayIn.setOnMouseExited(event -> {
            Image buttonPayInNormalImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/magicfruitsgame/images/button_payin_normal.png")));
            buttonPayIn.setGraphic(new ImageView(buttonPayInNormalImage));
        });

        buttonPayIn.setOnMousePressed(event -> {
            Image buttonPayInOnPressImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/magicfruitsgame/images/button_payin_onpress.png")));
            buttonPayIn.setGraphic(new ImageView(buttonPayInOnPressImage));
        });
    }

    private void updateBalanceLabel() {
        int balance = playerService.getBalance();
        balanceLabel.setText("Balance: " + balance);
    }

}
