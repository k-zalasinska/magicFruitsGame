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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

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


    public void evaluateResult(int[][] spinSymbols) {
        boolean horizontalWin = false;
        boolean diagonalWin = false;

        // Sprawdź poziome linie wygrywające
        for (int i = 0; i < 3; i++) {
            if (spinSymbols[i][0] == spinSymbols[i][1] && spinSymbols[i][1] == spinSymbols[i][2]) {
                horizontalWin = true;
                drawWinningLine(i, 0, i, 2); // Dodaj przekreślenie poziomej linii
            }
        }

        // Sprawdź przekątne linie
        if ((spinSymbols[0][0] == spinSymbols[1][1] && spinSymbols[1][1] == spinSymbols[2][2]) ||
                (spinSymbols[0][2] == spinSymbols[1][1] && spinSymbols[1][1] == spinSymbols[2][0])) {
            diagonalWin = true;
            // Dodaj przekreślenia dla obu przekątnych linii
            drawWinningLine(0, 0, 2, 2);
            drawWinningLine(0, 2, 2, 0);
        }

        // Obsługa wygranej
        if (horizontalWin || diagonalWin) {
            slotMachineService.calculateWinAmount(spinSymbols);
            System.out.println("Congratulations! You won " + slotMachineService.getLastWin() + " credits.");
        } else {
            slotMachineService.setLastWin(0);
            System.out.println("No win. Try again!");
        }

    }

    @FXML
    private Pane container; // Załóżmy, że masz kontroler z polem container reprezentującym kontener

    private void drawWinningLine(int startX, int startY, int endX, int endY) {
        // Zmienna lokalna dla metody drawWinningLine
        double symbolWidth = 100.0; // Przykładowa szerokość symbolu
        double symbolHeight = 100.0; // Przykładowa wysokość symbolu

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

}
