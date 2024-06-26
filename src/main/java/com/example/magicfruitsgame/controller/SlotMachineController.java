package com.example.magicfruitsgame.controller;

import com.example.magicfruitsgame.service.GameService;
import com.example.magicfruitsgame.service.SlotMachineService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;

import java.util.Optional;

import static com.example.magicfruitsgame.service.AlertHelper.showErrorAlert;
import static com.example.magicfruitsgame.service.AlertHelper.showInfoAlert;

/**
 * Controller class responsible for managing the slot machine UI logic.
 */
public class SlotMachineController {
    private final GameService gameService;
    private final SlotMachineService slotMachineService;

    @FXML
    private GridPane reelsGrid;

    @FXML
    private Label balanceLabel;

    @FXML
    private Label stakeLabel;

    @FXML
    private Label lastWinLabel;

    /**
     * Constructs a new instance of SlotMachineController.
     *
     * @param gameService        The game service instance.
     * @param slotMachineService The slot machine service instance.
     */
    public SlotMachineController(GameService gameService, SlotMachineService slotMachineService) {
        this.gameService = gameService;
        this.slotMachineService = slotMachineService;
    }

    /**
     * Handles the action when the start button is clicked.
     * Checks if there are sufficient funds on the balance to start the game by deducting the stake.
     * Initiates the spinning animation of the slot machine reels.
     * Updates the UI labels.
     * Informs the user if there are insufficient funds on the balance.
     */

    @FXML
    void startButtonClicked() {
        try {
            gameService.deduct();
            gameService.spinBoard();
            slotMachineService.startAnimation(reelsGrid);
            updateLabels();
        } catch (IllegalArgumentException e) {
            showErrorAlert("Insufficient Funds", e.getMessage());
        }
    }

    /**
     * Handles the action when the pay-in button is clicked.
     * Opens a dialog for the user to enter the deposit amount.
     * Validates the input and updates the balance accordingly.
     * Informs the user about an unsuccessful deposit attempt if the amount is invalid,
     * otherwise, notifies about the successful deposit.
     */
    @FXML
    void payInButtonClicked() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Deposit");
        dialog.setHeaderText("Enter the amount you want to deposit:");
        dialog.setContentText("Amount:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(amount -> {
            if (!amount.isEmpty()) {
                try {
                    int depositAmount = Integer.parseInt(amount);
                    gameService.deposit(depositAmount);
                    showInfoAlert("Deposit Success", "Balance has been successfully deposited.");
                } catch (NumberFormatException e) {
                    showErrorAlert("Invalid Amount", "Please enter a valid integer amount.");
                } catch (IllegalArgumentException e) {
                    showErrorAlert("Invalid Amount", e.getMessage());
                }
            } else {
                showErrorAlert("Invalid Amount", "Please enter a valid integer amount.");
            }
        });
        updateLabels();
    }


    /**
     * Initializes the controller after the FXML has been loaded.
     */
    @FXML
    public void initialize() {
        initializeReelsGrid();
        slotMachineService.startAnimation(reelsGrid);
        updateLabels();
    }

    /**
     * Updates the UI labels with the current balance, stake, and last win amount.
     */
    private void updateLabels() {
        Platform.runLater(() -> {
            balanceLabel.setText(Integer.toString(gameService.getBalance()));
            stakeLabel.setText(Integer.toString(gameService.getStake()));
            lastWinLabel.setText(Integer.toString(gameService.getLastWin()));
        });
    }


    /**
     * Initializes the grid for displaying symbols.
     * Sets its dimensions and alignment.
     */
    private void initializeReelsGrid() {
        reelsGrid.setMaxWidth(Double.MAX_VALUE);
        reelsGrid.setMaxHeight(Double.MAX_VALUE);
        reelsGrid.setAlignment(Pos.CENTER);
    }

}
