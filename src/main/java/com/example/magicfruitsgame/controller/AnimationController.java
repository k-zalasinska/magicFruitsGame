package com.example.magicfruitsgame.controller;

import com.example.magicfruitsgame.service.SlotMachineService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AnimationController {
    private final SlotMachineService slotMachineService;

    @FXML
    private ImageView reelImageView1;

    @FXML
    private ImageView reelImageView2;

    @FXML
    private ImageView reelImageView3;


    @FXML
    private Label balanceLabel;

    @FXML
    private Label lastWinLabel;

    @FXML
    private Label stakeLabel;

    public AnimationController(SlotMachineService slotMachineService) {
        this.slotMachineService = slotMachineService;
    }

    // Metoda obsługująca naciśnięcie przycisku "START"
    @FXML
    private void handleStartButton() {
        // Rozpoczyna animację obracania bębnów
        int[][] spinSymbols = slotMachineService.spinReels();
        slotMachineService.startSpinningAnimation(new ImageView[][]{{reelImageView1}, {reelImageView2}, {reelImageView3}}, spinSymbols);

        // Wykonuje rozgrywkę
        slotMachineService.play();
        updateLabels();
    }

    // Metoda aktualizująca etykiety na interfejsie użytkownika
    private void updateLabels() {
        balanceLabel.setText("Balance: " + slotMachineService.getBalance());
        lastWinLabel.setText("Last Win: " + slotMachineService.getLastWin());
        stakeLabel.setText("Stake: " + slotMachineService.getStake());
    }

    private void updateReelImages(int[][] spinSymbols) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int symbolIndex = spinSymbols[i][j];
                Image symbolImage = slotMachineService.getSymbolImage(symbolIndex);
                ImageView imageView = getReelImageView(i, j);
                imageView.setImage(symbolImage);
            }
        }
    }

    private ImageView getReelImageView(int i, int j) {
        return switch (i) {
            case 0 -> reelImageView1;
            case 1 -> reelImageView2;
            case 2 -> reelImageView3;
            default -> throw new IllegalArgumentException("Invalid reel index: " + i);
        };
    }
}
