package com.example.magicfruitsgame.controller;

import com.example.magicfruitsgame.service.SlotMachineService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.ResourceBundle;

public class SlotMachineController implements Initializable {
    private SlotMachineService slotMachineService;
    private ImageView[][] reelImageViews;

    @FXML
    private GridPane reelsImageViews;

    @FXML
    private Button startButton;

    @FXML
    private TextField depositAmountField;

    @FXML
    private Button payInButton;

    @FXML
    private Label balanceLabel;

    @FXML
    private Label lastWinLabel;

    @FXML
    private Label stakeLabel;

    public SlotMachineController(SlotMachineService slotMachineService) {
        this.slotMachineService = slotMachineService;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Node> children = reelsImageViews.getChildren();

        reelImageViews = new ImageView[3][3]; // Inicjalizacja pola reelImageViews

        int index = 0;

        for (Node child : children) {
            if (child instanceof ImageView imageView) {
                int row = index / 3;
                int col = index % 3;
                reelImageViews[row][col] = imageView;
                index++;
            }
        }
//        reelImageViews[row][col] = (ImageView) child;
        // Ustawienie reelImageViews w SlotMachineService
        slotMachineService.setReelImageViews(reelImageViews);

        balanceLabel.setText("100");
        lastWinLabel.setText("0");
        stakeLabel.setText("10");

        // Dodanie obsługi zdarzeń dla przycisku "Start"
        startButton.setOnAction(event -> handleStartButton());

        depositAmountField.setVisible(false);
        payInButton.setOnAction(event -> handlePayInButton());

        balanceLabel.setTranslateX(550);
        balanceLabel.setTranslateY(815);

        lastWinLabel.setTranslateX(762);
        lastWinLabel.setTranslateY(770);

        stakeLabel.setTranslateX(939);
        stakeLabel.setTranslateY(722);

        updateUI();
    }

    // Metoda obsługująca kliknięcie przycisku "Start"
    @FXML
    private void handleStartButton() {
        int[] spinSymbols = slotMachineService.spinReels();
        if (spinSymbols != null && spinSymbols.length > 0) {
            slotMachineService.startSpinningAnimation(spinSymbols);
        } else {
            System.err.println("Error: Unable to retrieve symbols for spinning.");
        }
    }

    // Metoda obsługująca "Pay in"
    @FXML
    private void handlePayInButton() {
        depositAmountField.setVisible(true);
        depositAmountField.requestFocus();
        depositAmountField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                processDeposit();
            }
        });
    }

    @FXML
    private void processDeposit() {
        String amountText = depositAmountField.getText();
        try {
            int depositAmount = Integer.parseInt(amountText);
            if (depositAmount > 0) {
                performDeposit(depositAmount);
                depositAmountField.setVisible(false);
                depositAmountField.clear();
                System.out.println("Deposit processed successfully");
            } else {
                System.err.println("Invalid input. Please enter a positive number.");
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid input. Please enter a valid number.");
        }
    }

    private void performDeposit(int depositAmount) {
        slotMachineService.topUpBalance(depositAmount);
        updateUI();
        System.out.println("Deposit amount: " + depositAmount);
    }

    @FXML
    private void initialize() {
        depositAmountField = new TextField();

    }

    private void updateUI() {
        int balance = slotMachineService.getBalance();
        int lastWin = slotMachineService.getLastWin();
        int stake = slotMachineService.getStake();

        // Ustawia wartości na kontrolkach UI
        balanceLabel.setText("" + balance);
        lastWinLabel.setText("" + lastWin);
        stakeLabel.setText("" + stake);
    }

    public void setSlotMachineService(SlotMachineService slotMachineService) {
        this.slotMachineService = slotMachineService;
    }

    public ImageView[][] getReelImageViews() {
        return reelImageViews;
    }

    @FXML
    private Pane container;

    private void drawWinningLine(int startX, int startY, int endX, int endY) {
        double symbolWidth = 100.0;
        double symbolHeight = 100.0;

        Line line = new Line();
        line.setStartX(startX * symbolWidth + symbolWidth / 2);
        line.setStartY(startY * symbolHeight + symbolHeight / 2);
        line.setEndX(endX * symbolWidth + symbolWidth / 2);
        line.setEndY(endY * symbolHeight + symbolHeight / 2);
        line.setStrokeWidth(3);
        line.setStroke(Color.RED);
        // Dodaj przekreślenie do kontenera
        container.getChildren().add(line);
    }

    @FXML
    private void evaluateResult(int[] spinSymbols) {
        slotMachineService.evaluateResult(spinSymbols);
        updateUI();

    }
}
