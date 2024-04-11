package com.example.magicfruitsgame.controller;

import com.example.magicfruitsgame.service.GameService;
import com.example.magicfruitsgame.service.SlotMachineService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class SlotMachineController implements Initializable {

    @FXML
    private Button spinButton;

    @FXML
    private ImageView slotMachineImageView;

    private final SlotMachineService slotMachineService;
    private final GameService gameService;

    public SlotMachineController(SlotMachineService slotMachineService, GameService gameService) {
        this.slotMachineService = slotMachineService;
        this.gameService = gameService;
    }

    @FXML
    private void handleSpinButton() {
        if (gameService.isGameRunning()) {
            int[][] symbols = slotMachineService.spinReels();
            slotMachineService.evaluateResult(symbols);
            updateUI();
        } else {
            gameService.startGame();
            updateUI();
        }
    }

    @FXML
    private void handleSpinButtonHover() {
        if (gameService.isGameRunning()) {
            spinButton.setStyle("-fx-background-image: url('button_start_onhover.png');");
        }
    }

    @FXML
    private void handleSpinButtonPress() {
        if (gameService.isGameRunning()) {
            spinButton.setStyle("-fx-background-image: url('button_start_onpress.png');");
        }
    }

    @FXML
    private void handleSpinButtonExit() {
        if (gameService.isGameRunning()) {
            spinButton.setStyle("-fx-background-image: url('button_start_normal.png');");
        } else {
            spinButton.setStyle("-fx-background-image: url('button_start_disable.png');");
        }
    }

    private void updateUI() {
        if (gameService.isGameRunning()) {
            spinButton.setStyle("-fx-background-image: url('button_start_normal.png');");
            slotMachineImageView.setImage(new Image("button_start_normal.png"));
        } else {
            spinButton.setStyle("-fx-background-image: url('button_start_disable.png');");
            spinButton.setStyle("-fx-background-image: url('button_start_onhover.png');");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateUI();
    }
}
