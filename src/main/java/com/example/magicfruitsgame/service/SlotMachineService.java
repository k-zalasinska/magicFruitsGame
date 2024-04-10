package com.example.magicfruitsgame.service;

import com.example.magicfruitsgame.model.Game;
import com.example.magicfruitsgame.model.Symbol;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Random;

public class SlotMachineService {
    //    private static final int SYMBOL_HEIGHT = 100;
    private static final int REEL_COUNT = 3;
    private static final int SYMBOL_COUNT = 3;
    private static final int ANIMATION_DURATION = 9000; //9 sek.łącznie. Po 3 sek.na symbol

    private final Game game;
    private final Symbol[] symbols;
    private final int[][] reelsDefinition;

    private ImageView[][] reelImageViews;
    private int[][] spinSymbols;
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
        int frameDuration=3000; //czas trwania jednegp KeyFrame'a
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(frameDuration), e -> updateReels()));
        timeline.setCycleCount(ANIMATION_DURATION / frameDuration); // Określ liczby cykli na podstawie całkow czasu trwania animacji i czasu trwania jednego cyklu (KeyFrame)
        timeline.setOnFinished(e -> evaluateResult());
        timeline.play();
    }


    private void updateReels() {
        for (int i = 0; i < REEL_COUNT; i++) {
            for (int j = 0; j < SYMBOL_COUNT; j++) {
                int symbolIndex = spinSymbols[i][j];
                Symbol symbol = symbols[symbolIndex];
                reelImageViews[i][j].setImage(symbol.getImage());
            }
        }


        // Przesunięcie symboli do góry (pseudo animacja)
        int[][] newSymbols = new int[REEL_COUNT][SYMBOL_COUNT];
        for (int i = 0; i < REEL_COUNT; i++) {
            newSymbols[i][0] = spinSymbols[i][SYMBOL_COUNT - 1];
            System.arraycopy(spinSymbols[i], 0, newSymbols[i], 1, SYMBOL_COUNT - 1);
        }
        spinSymbols = newSymbols;
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
        for (int i = 0; i < REEL_COUNT; i++) {
            if (spinSymbols[i][0] != spinSymbols[i][1] || spinSymbols[i][1] != spinSymbols[i][2]) {
                return false;
            }
        }
        return true;
    }

    private int calculateWinAmount() {
        int symbolIndex = spinSymbols[0][0]; // Get any symbol index from the first reel

        int winMultiplier = symbols[symbolIndex].getWinMultiplier();
        return game.getStake() * winMultiplier;
    }
}
