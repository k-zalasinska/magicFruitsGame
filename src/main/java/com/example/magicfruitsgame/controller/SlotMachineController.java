package com.example.magicfruitsgame.controller;

import com.example.magicfruitsgame.model.Game;
import com.example.magicfruitsgame.service.GameService;
import com.example.magicfruitsgame.service.ReelService;
import com.example.magicfruitsgame.service.SlotMachineService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    public GridPane reelsGridPane;
    private SlotMachineService slotMachineService;

    private final int[][] reels = {{0, 4, 0, 0, 0, 2, 2, 3, 1, 4, 0, 6, 0, 1, 0, 0, 5, 1, 1, 3},
            {2, 5, 1, 2, 0, 1, 3, 5, 2, 6, 1, 0, 0, 0, 1, 3, 4, 4, 0, 4},
            {5, 2, 1, 0, 0, 3, 4, 0, 2, 6, 0, 2, 0, 0, 0, 1, 1, 3, 0, 4}};

    @FXML
    private ImageView[][] reelImageViews;

    @FXML
    private ImageView reelImageView1_1;

    @FXML
    private ImageView reelImageView1_2;

    @FXML
    private ImageView reelImageView1_3;

    @FXML
    private ImageView reelImageView2_1;

    @FXML
    private ImageView reelImageView2_2;

    @FXML
    private ImageView reelImageView2_3;

    @FXML
    private ImageView reelImageView3_1;

    @FXML
    private ImageView reelImageView3_2;

    @FXML
    private ImageView reelImageView3_3;

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

    public SlotMachineController(ReelService reelService, GameService gameService) {
        this.slotMachineService = new SlotMachineService(reelService, gameService);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Inicjalizacja ReelService i GameService
        ReelService reelService = new ReelService(reels);
        GameService gameService = new GameService(new Game());

        // Inicjalizacja tablicy reelImageViews
        ImageView[][] reelImageViews = new ImageView[][]{
                {reelImageView1_1, reelImageView1_2, reelImageView1_3},
                {reelImageView2_1, reelImageView2_2, reelImageView2_3},
                {reelImageView3_1, reelImageView3_2, reelImageView3_3}};


        // Utworzenie instancji SlotMachineService
        this.slotMachineService = new SlotMachineService(reelService, gameService);
        slotMachineService.setReelImageViews(reelImageViews);

        // Inicjalizacja tablicy reelImageViews
        initializeReelImageViews();

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

    // Metoda inicjalizująca pola ImageView
    public void initializeReelImageViews() {
        reelImageViews = new ImageView[3][3];
        reelImageViews[0] = new ImageView[]{reelImageView1_1, reelImageView1_2, reelImageView1_3};
        reelImageViews[1] = new ImageView[]{reelImageView2_1, reelImageView2_2, reelImageView2_3};
        reelImageViews[2] = new ImageView[]{reelImageView3_1, reelImageView3_2, reelImageView3_3};
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
        int currentBalance = slotMachineService.getBalance();
        int newBalance = currentBalance + depositAmount;
        slotMachineService.topUpBalance(newBalance);
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

    public SlotMachineController() {
    }

    @FXML
    private void evaluateResult(int[] spinSymbols) {
        slotMachineService.evaluateResult(spinSymbols);
        updateUI();

    }
}
