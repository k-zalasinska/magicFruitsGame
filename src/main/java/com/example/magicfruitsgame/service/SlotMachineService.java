package com.example.magicfruitsgame.service;

import com.example.magicfruitsgame.model.Game;
import com.example.magicfruitsgame.model.Symbol;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Random;

public class SlotMachineService {
    //private static final int SYMBOL_HEIGHT = 100;
    private static final int REEL_COUNT = 3;
    private static final int SYMBOL_COUNT = 3; //lb symboli na każdym bębnie
    private static final int ANIMATION_DURATION = 9000; //całkowity czas trwania animacji. 9 sek.łącznie. Po 3 sek.na symbol

    private final Game game;
    private final Symbol[] symbols;
    private final int[][] reelsDefinition; //służy do definiowania możliwych kombinacji symboli na każdym z bębnów (kolumn) w grze

    private ImageView[][] reelImageViews;
    private int[][] spinSymbols; //reprezentuje aktualny układ symboli na bębnach w trakcie gry.
    public int winAmount;

    public SlotMachineService(Game game, Symbol[] symbols, int[][] reelsDefinition) {
        this.game = game;
        this.symbols = symbols;
        this.reelsDefinition = reelsDefinition;
    }

    public void play(ImageView[][] reelImageViews) {
        this.reelImageViews = reelImageViews;
        this.spinSymbols = drawSymbols();

        animateSpinning();
    }

    private int[][] drawSymbols() {
        Random random = new Random();
        int[][] symbols = new int[REEL_COUNT][SYMBOL_COUNT];
        for (int i = 0; i < REEL_COUNT; i++) {
            for (int j = 0; j < SYMBOL_COUNT; j++) {
                symbols[i][j] = reelsDefinition[i][random.nextInt(reelsDefinition[i].length)];
            }
        }
        return symbols;
    }

    private void animateSpinning() {
        int frameDuration = 3000; //czas trwania jednegp KeyFrame'a
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(frameDuration), e -> updateReels()));
        timeline.setCycleCount(ANIMATION_DURATION / frameDuration); // Określ liczby cykli na podstawie całkow czasu trwania animacji i czasu trwania jednego cyklu (KeyFrame)
        timeline.setOnFinished(e -> evaluateResult());
        timeline.play();
    }

    private void updateReels() {
        int frameDuration = ANIMATION_DURATION / SYMBOL_COUNT; //oblicza długość trwania pojedynczego obrotu walców
        Timeline timeline = new Timeline();
        for (int i = 0; i < REEL_COUNT; i++) {
            for (int j = 0; j < SYMBOL_COUNT; j++) {
                int symbolIndex = spinSymbols[i][j];
                Symbol symbol = symbols[symbolIndex];
                ImageView imageView = reelImageViews[i][j];
                KeyFrame keyFrame = new KeyFrame(Duration.millis(frameDuration * j), e -> imageView.setImage(symbol.getImage()));
                timeline.getKeyFrames().add(keyFrame);
            }
        }
        timeline.play();
    }

    private void evaluateResult() {
        if (checkWin()) {
            winAmount = calculateWinAmount();
            game.setBalance(game.getBalance() + winAmount);
            game.setLastWin(winAmount);
            System.out.println("Congratulations! You won " + winAmount + " credits.");
        } else {
            game.setLastWin(0);
            System.out.println("No win. Try again!");
        }
    }

    private boolean checkWin() {
        //sprawdz poziomych linii wygryw
        for (int i = 0; i < REEL_COUNT; i++) {
            if (spinSymbols[i][0] == spinSymbols[i][1] && spinSymbols[i][1] == spinSymbols[i][2]) {
                return true;
            }
        }

        //sprawdzenie przekątnych linii
        if ((spinSymbols[0][0] == spinSymbols[1][1] && spinSymbols[1][1] == spinSymbols[2][2]) ||
                (spinSymbols[0][2] == spinSymbols[1][1] && spinSymbols[1][1] == spinSymbols[2][0])) {
            return true;
        }

        return false;
    }

    private int calculateWinAmount() {
        int symbolIndex = spinSymbols[0][0]; // Get any symbol index from the first reel

        int winMultiplier = symbols[symbolIndex].getWinMultiplier();
        return game.getStake() * winMultiplier;
    }
}
