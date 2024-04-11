package com.example.magicfruitsgame.controller;

import com.example.magicfruitsgame.service.SlotMachineService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class SlotMachineController implements Initializable {

    @FXML
    private Button spinButton;

    @FXML
    private ImageView slotMachineImageView;

    @FXML
    private TextField depositAmountField;

    private final SlotMachineService slotMachineService;

    public SlotMachineController(SlotMachineService slotMachineService) {
        this.slotMachineService = slotMachineService;
    }

    @FXML
    private void handleSpinButton() {
        if (slotMachineService.getGameService().isGameRunning()) {
            int[][] symbols = slotMachineService.spinReels();
            slotMachineService.evaluateResult(symbols);
            updateUI();
        } else {
            slotMachineService.getGameService().startGame();
            updateUI();
        }
    }

    @FXML
    private void handleSpinButtonHover() {
        if (slotMachineService.getGameService().isGameRunning()) {
            spinButton.setStyle("-fx-background-image: url('button_start_onhover.png');");
        }
    }

    @FXML
    private void handleSpinButtonPress() {
        if (slotMachineService.getGameService().isGameRunning()) {
            spinButton.setStyle("-fx-background-image: url('button_start_onpress.png');");
        }
    }

    @FXML
    private void handleSpinButtonExit() {
        if (slotMachineService.getGameService().isGameRunning()) {
            spinButton.setStyle("-fx-background-image: url('button_start_normal.png');");
        } else {
            spinButton.setStyle("-fx-background-image: url('button_start_disable.png');");
        }
    }

    private void updateUI() {
        if (slotMachineService.getGameService().isGameRunning()) {
            spinButton.setStyle("-fx-background-image: url('button_start_normal.png');");
            slotMachineImageView.setImage(new Image("button_start_normal.png"));
        } else {
            spinButton.setStyle("-fx-background-image: url('button_start_disable.png');");
            slotMachineImageView.setImage(new Image("button_start_disable.png"));
        }
    }

    @FXML
    private void handlePayInButton() {
        // Tworzymy okno dialogowe do wprowadzenia kwoty doładowania
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Deposit");
        dialog.setHeaderText("Enter the amount to deposit:");
        dialog.setContentText("Amount:");

        // Pobieramy wprowadzoną kwotę z okna dialogowego
        dialog.showAndWait().ifPresent(amount -> {
            try {
                int depositAmount = Integer.parseInt(amount);
                slotMachineService.getGameService().deposit(depositAmount);
                updateUI();
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a valid number.");
            }
        });
    }


    @FXML
    private void handlePayInButtonHover() {
        if (slotMachineService.getGameService().isGameRunning()) {
            spinButton.setStyle("-fx-background-image: url('button_payin_onhover.png');");
        }
    }

    @FXML
    private void handlePayInButtonPress() {
        if (slotMachineService.getGameService().isGameRunning()) {
            spinButton.setStyle("-fx-background-image: url('button_payin_onpress.png');");
        }
    }

    @FXML
    private void handlePayInButtonExit() {
        if (slotMachineService.getGameService().isGameRunning()) {
            spinButton.setStyle("-fx-background-image: url('button_payin_normal.png');");
        } else {
            spinButton.setStyle("-fx-background-image: url('button_payin_disable.png');");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateUI();
    }
}
