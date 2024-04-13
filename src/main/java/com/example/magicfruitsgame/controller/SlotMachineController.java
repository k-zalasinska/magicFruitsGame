package com.example.magicfruitsgame.controller;

import com.example.magicfruitsgame.model.Game;
import com.example.magicfruitsgame.service.GameService;
import com.example.magicfruitsgame.service.ReelService;
import com.example.magicfruitsgame.service.SlotMachineService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SlotMachineController implements Initializable {
    private SlotMachineService slotMachineService;

    private final int[][] reels = {
            {0, 4, 0, 0, 0, 2, 2, 3, 1, 4, 0, 6, 0, 1, 0, 0, 5, 1, 1, 3},
            {2, 5, 1, 2, 0, 1, 3, 5, 2, 6, 1, 0, 0, 0, 1, 3, 4, 4, 0, 4},
            {5, 2, 1, 0, 0, 3, 4, 0, 2, 6, 0, 2, 0, 0, 0, 1, 1, 3, 0, 4}
    };

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
    private Label balanceLabel;
    @FXML
    private Label lastWinLabel;
    @FXML
    private Label stakeLabel;
    @FXML
    private Button spinButton;
    @FXML
    private ImageView slotMachineImageView;

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private VBox container;

    public SlotMachineController() {
        // Konstruktor bezparametrowy
    }

    public SlotMachineController(ReelService reelService, GameService gameService) {
        this.slotMachineService = new SlotMachineService(reelService, gameService);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Inicjalizacja tablicy reelImageViews
        initializeReelImageViews();

        // Dodanie obsługi zdarzeń dla przycisku "Start"
        startButton.setOnAction(event -> handleStartButton());

        // Inicjalizacja ReelService i GameService
        ReelService reelService = new ReelService(reels);
        GameService gameService = new GameService(new Game());

        // Inicjalizacja tablicy reelImageViews
        ImageView[][] reelImageViews = new ImageView[][]{
                {reelImageView1_1, reelImageView1_2, reelImageView1_3},
                {reelImageView2_1, reelImageView2_2, reelImageView2_3},
                {reelImageView3_1, reelImageView3_2, reelImageView3_3}
        };

        // Utworzenie instancji SlotMachineService
        this.slotMachineService = new SlotMachineService(reelService, gameService);
        slotMachineService.setReelImageViews(reelImageViews);
        // Aktualizacja interfejsu użytkownika
        updateUI();
    }


    // Metoda do aktualizacji interfejsu użytkownika
    private void updateUI() {
        // Pobierz wartości do wyświetlenia
        int balance = slotMachineService.getBalance();
        int lastWin = slotMachineService.getLastWin();
        int stake = slotMachineService.getStake();

        // Ustaw wartości na kontrolkach UI
        balanceLabel.setText("Balance: " + balance);
        lastWinLabel.setText("Last Win: " + lastWin);
        stakeLabel.setText("Stake: " + stake);

        // Ustawienie stylu przycisku "Start" w zależności od stanu gry
        if (slotMachineService.getGameService().isGameRunning()) {
            startButton.setStyle("-fx-background-image: url('views/button_start_normal.png');");
            slotMachineImageView.setImage(new Image("views/button_start_normal.png"));
        } else {
            startButton.setStyle("-fx-background-image: url('views/button_start_disable.png');");
            slotMachineImageView.setImage(new Image("views/button_start_disable.png"));
        }
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
        int[][] spinSymbols = slotMachineService.spinReels();
        slotMachineService.startSpinningAnimation(spinSymbols);
    }

    // Metoda obsługująca kliknięcie przycisku "Deposit"
    @FXML
    private void handlePayInButton() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Deposit");
        dialog.setHeaderText("Enter the amount to deposit:");
        dialog.setContentText("Amount:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(amount -> {
            try {
                int depositAmount = Integer.parseInt(amount);
                slotMachineService.topUpBalance(depositAmount);
                updateUI();
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a valid number.");
            }
        });
    }

    // Metoda obsługująca zdarzenie najechania kursorem na przycisk "Start"
    @FXML
    private void handleSpinButtonHover() {
        if (slotMachineService.getGameService().isGameRunning()) {
            spinButton.setStyle("-fx-background-image: url('views/button_start_onhover.png');");
        }
    }

    // Metoda obsługująca zdarzenie naciśnięcia przycisku "Start"
    @FXML
    private void handleSpinButtonPress() {
        if (slotMachineService.getGameService().isGameRunning()) {
            spinButton.setStyle("-fx-background-image: url('views/button_start_onpress.png');");
        }
    }

    // Metoda obsługująca zdarzenie zakończenia najechania kursorem na przycisk "Start"
    @FXML
    private void handleSpinButtonExit() {
        if (slotMachineService.getGameService().isGameRunning()) {
            spinButton.setStyle("-fx-background-image: url('views/button_start_normal.png');");
        } else {
            spinButton.setStyle("-fx-background-image: url('views/button_start_disable.png');");
        }
    }

    // Metoda obsługująca zdarzenie najechania kursorem na przycisk "Deposit"
    @FXML
    private void handlePayInButtonHover() {
        if (slotMachineService.getGameService().isGameRunning()) {
            spinButton.setStyle("-fx-background-image: url('views/button_payin_onhover.png');");
        }
    }

    // Metoda obsługująca zdarzenie naciśnięcia przycisku "Deposit"
    @FXML
    private void handlePayInButtonPress() {
        if (slotMachineService.getGameService().isGameRunning()) {
            spinButton.setStyle("-fx-background-image: url('views/button_payin_onpress.png');");
        }
    }

    // Metoda obsługująca zdarzenie zakończenia najechania kursorem na przycisk "Deposit"
    @FXML
    private void handlePayInButtonExit() {
        if (slotMachineService.getGameService().isGameRunning()) {
            spinButton.setStyle("-fx-background-image: url('views/button_payin_normal.png');");
        } else {
            spinButton.setStyle("-fx-background-image: url('views/button_payin_disable.png');");
        }
    }

    // Metoda do ustawiania usługi SlotMachineService
    public void setSlotMachineService(SlotMachineService slotMachineService) {
        this.slotMachineService = slotMachineService;
    }

    public ImageView[][] getReelImageViews() {
        return reelImageViews;
    }
}
