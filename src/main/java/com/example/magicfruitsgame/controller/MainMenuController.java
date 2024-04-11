package com.example.magicfruitsgame.controller;

import com.example.magicfruitsgame.service.GameService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.util.Optional;

public class MainMenuController {

    public GameService playerService;
    public GameService gameService;

    public MainMenuController(GameService playerService, GameService gameService) {
        this.playerService = playerService;
        this.gameService = gameService;
    }

    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    @FXML
    public Label titleLabel;

    @FXML
    public Button playButton;

    @FXML
    public Button optionsButton;

    @FXML
    public Button exitButton;


    // Metody obsługujące interakcje użytkownika
    @FXML
    public void handlePlay() {
        System.out.println("Play button clicked!"); // logika uruchom. przyc. PLAY
    }

    @FXML
    public void handleOptions() {
        System.out.println("Options button clicked!"); // Logika wyświetlenia opcji
    }

    @FXML
    public void handleExit() {                                     // Logika zamknięcia gry
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Confirmation");
        alert.setHeaderText("Are you sure you want to exit the game?");
        alert.setContentText("Click OK to exit or Cancel to continue playing.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }
}
