package com.example.magicfruitsgame.service;

import com.example.magicfruitsgame.model.Symbol;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SlotMachineService {
    private final SymbolService symbolService;
    private final ReelService reelService;
    private final GameService gameService;
    private int lastWin;
    public SlotMachineService(SymbolService symbolService, ReelService reelService, GameService gameService) {
        this.symbolService=symbolService;
        this.reelService = reelService;
        this.gameService = gameService;
    }
    public GameService getGameService() {
        return gameService;
    }
    public int getLastWin() {
        return lastWin;
    }

    public void setLastWin(int lastWin) {
        this.lastWin = lastWin;
    }

    public int[] play(int[][] reelsDefinition) {
        int[][] reelsSymbols = new int[3][];
        ReelService reelService = new ReelService(reelsDefinition);

        // Obrót każdego z trzech bębnów
        for (int i = 0; i < 3; i++) {
            reelsSymbols[i] = reelService.spin(i);
        }

        return reelsSymbols[1];
    }


    public void animateSpinning(ImageView[][] reelImageViews, int[][] spinSymbols) {
        int frameDuration = 3; //czas trwania jednego KeyFrame'a
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(frameDuration), e -> updateReels(reelImageViews, spinSymbols)));
        timeline.setCycleCount(9 / frameDuration); // Określa liczbę cykli na podstawie całk czasu trw animacji i czasu trw jednego cyklu (KeyFrame)
        timeline.play();
    }

    private void updateReels(ImageView[][] reelImageViews, int[][] spinSymbols) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int symbolIndex = spinSymbols[i][j];
                Symbol symbol =symbolService.getSymbols().get(symbolIndex);
                ImageView imageView = reelImageViews[i][j];
                imageView.setImage(symbol.image());
            }
        }
    }

    public void evaluateResult(int[][] spinSymbols) {
        if (checkWin(spinSymbols)) {
            calculateWinAmount(spinSymbols);
            System.out.println("Congratulations! You won " + getLastWin() + " credits.");
        } else {
            setLastWin(0);
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
            checkWin(symbols);
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

    private void checkWin(int[] symbols) {
        if (symbols[0] == symbols[1] && symbols[1] == symbols[2]) {
            int multiplier = symbolService.getSymbol(symbols[0]).winMultiplier();
            int winAmount = 10 * multiplier;
            setLastWin(winAmount);
        }
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
