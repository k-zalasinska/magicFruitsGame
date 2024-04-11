package com.example.magicfruitsgame.service;

import com.example.magicfruitsgame.model.Game;

import com.example.magicfruitsgame.model.Symbol;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SlotMachineService {
    private final Game game;
    private final ReelService reelService;
    private final GameService gameService;

    public SlotMachineService(Game game, ReelService reelService,GameService gameService) {
        this.game = game;
        this.reelService = reelService;
        this.gameService = gameService;
    }

    public int[] play(int[][] reelsDefinition) {
        int[][] reelsSymbols = new int[3][];
        ReelService reelService = new ReelService(reelsDefinition);

        // Obrót każdego z trzech bębnów
        for (int i = 0; i < 3; i++) {
            reelsSymbols[i] = reelService.spin(i); // Wywołanie metody spin dla każdego z bębnów
        }

        return reelsSymbols[1]; // Zwracamy symbole z środkowego bębna
    }


    public void animateSpinning(ImageView[][] reelImageViews, int[][] spinSymbols) {
        int frameDuration = 3; //czas trwania jednego KeyFrame'a
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(frameDuration), e -> updateReels(reelImageViews, spinSymbols)));
        timeline.setCycleCount(9 / frameDuration); // Określ liczbę cykli na podstawie całkowitego czasu trwania animacji i czasu trwania jednego cyklu (KeyFrame)
        timeline.play();
    }

    private void updateReels(ImageView[][] reelImageViews, int[][] spinSymbols) {
        int frameDuration = 9 / 3; //oblicza długość trwania pojedynczego obrotu walców
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int symbolIndex = spinSymbols[i][j];
                Symbol symbol = gameService.getSymbols()[symbolIndex];
                ImageView imageView = reelImageViews[i][j];
                imageView.setImage(symbol.getImage());
            }
        }
    }

    public void evaluateResult(int[][] spinSymbols) {
        if (checkWin(spinSymbols)) {
            calculateWinAmount(spinSymbols);
            game.setBalance(game.getBalance() + game.getLastWin());
            System.out.println("Congratulations! You won " + game.getLastWin() + " credits.");
        } else {
            game.setLastWin(0);
            System.out.println("No win. Try again!");
        }
    }

    private boolean checkWin(int[][] spinSymbols) {
        // Sprawdź poziome linie wygrywające
        for (int i = 0; i < 3; i++) {
            if (spinSymbols[i][0] == spinSymbols[i][1] && spinSymbols[i][1] == spinSymbols[i][2]) {
                return true;
            }
        }

        // Sprawdź przekątne linie
        if ((spinSymbols[0][0] == spinSymbols[1][1] && spinSymbols[1][1] == spinSymbols[2][2]) ||
                (spinSymbols[0][2] == spinSymbols[1][1] && spinSymbols[1][1] == spinSymbols[2][0])) {
            return true;
        }

        return false;
    }

    private void calculateWinAmount(int[][] spinSymbols) {
        // Sprawdza trzy poziome linie
        for (int row = 0; row < 3; row++) {
            int[] symbols = new int[3]; // Przechowuje symbole na danej linii
            for (int col = 0; col < 3; col++) {
                symbols[col] = spinSymbols[col][row];
            }
            checkWin(symbols); // Sprawdź wygraną dla danej linii
        }

        // Sprawdza dwie linie na ukos
        int[] diagonalSymbols1 = new int[3]; // Pierwsza linia na ukos
        int[] diagonalSymbols2 = new int[3]; // Druga linia na ukos
        for (int i = 0; i < 3; i++) {
            diagonalSymbols1[i] = spinSymbols[i][i];
            diagonalSymbols2[i] = spinSymbols[i][2 - i];
        }
        checkWin(diagonalSymbols1); // Sprawdza wygraną dla pierwszej linii na ukos
        checkWin(diagonalSymbols2); // Sprawdza wygraną dla drugiej linii na ukos
    }

    private boolean checkWin(int[] symbols) {
        // Sprawdź, czy wszystkie symbole na danej linii są takie same
        if (symbols[0] == symbols[1] && symbols[1] == symbols[2]) {
            int multiplier = game.getMultiplier(symbols[0]);
            int winAmount = game.getStake() * multiplier;
            game.setLastWin(winAmount);
        }
        return false;
    }

    // Metoda do obracania bębnami
    public int[][] spinReels() {
        int[][] reelsSymbols = new int[3][];
        for (int i = 0; i < 3; i++) {
            reelsSymbols[i] = reelService.spin(i);
        }
        return reelsSymbols;
    }


}
