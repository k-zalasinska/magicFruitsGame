package com.example.magicfruitsgame.controller;

import com.example.magicfruitsgame.service.GameService;
import com.example.magicfruitsgame.service.SlotMachineService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;
import java.util.Optional;

import static com.example.magicfruitsgame.service.AlertHelper.showErrorAlert;
import static com.example.magicfruitsgame.service.AlertHelper.showInfoAlert;

public class SlotMachineController {

    private final GameService gameService;
    private final SlotMachineService slotMachineService;
    private int lastWin;

    @FXML
    private ImageView[] reel1;

    @FXML
    private ImageView[] reel2;

    @FXML
    private ImageView[] reel3;
//
//    @FXML
//    private GridPane reelsGrid;

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


    public SlotMachineController(GameService gameService, SlotMachineService slotMachineService) {
        this.gameService = gameService;
        this.slotMachineService = slotMachineService;
    }

    @FXML
    public void initialize() {
        setImageOnButton(startButton, "/views/button_start_normal.png");
        setImageOnButton(payInButton, "/views/button_payin_normal.png");

        slotMachineService.startSpinAnimation(reel1, reel2, reel3);
//        initializeReelsGrid();
    }

    @FXML
    private void handleStartButton() {
        try {
            gameService.deduct();
        } catch (IllegalArgumentException e) {
            showErrorAlert("Insufficient Funds", e.getMessage());
            return;
        }
        slotMachineService.startSpinAnimation(reel1, reel2, reel3);
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

//    private void initializeReelsGrid() {
//        //wymiary siatki symb
//        reelsGrid.setPrefWidth(500);
//        reelsGrid.setPrefHeight(400);
//        reelsGrid.setMaxWidth(Double.MAX_VALUE);
//        reelsGrid.setMaxHeight(Double.MAX_VALUE);
//
//        //ustawia siatkę symb na środku rodzica- background
//        reelsGrid.setAlignment(Pos.CENTER);
//    }

    private void setImageOnButton(Button button, String imagePath) {
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath))));
        button.setGraphic(imageView);
    }

}
