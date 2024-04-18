package com.example.magicfruitsgame.service;

import com.example.magicfruitsgame.model.Symbol;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.util.Random;

public class GameService {
    private static final Random random = new Random();
    private final SymbolService symbolService;
    private final int stake = 10;
    private int balance = 1000;

    public GameService(SymbolService symbolService) {
        this.symbolService = symbolService;
    }

    public Symbol[][] spinBoard() {
        Symbol[][] board = new Symbol[3][3];
        int[] startingIndices = {random.nextInt(symbolService.getSymbols().size()), random.nextInt(symbolService.getSymbols().size()), random.nextInt(symbolService.getSymbols().size())};
        for (int i = 0; i < 3; i++) {
            Symbol[] reelSymbols = new Symbol[3];
            for (int j = 0; j < 3; j++) {
                int symbolIndex = (startingIndices[i] + j) % symbolService.getSymbols().size();
                reelSymbols[j] = symbolService.getSymbol(symbolIndex);
            }
            board[i] = reelSymbols;
        }
        return board;
    }

    public void animateSpin(GridPane reelsGrid) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(1000), event -> shiftSymbols(reelsGrid)),
                new KeyFrame(Duration.millis(2000), event -> shiftSymbols(reelsGrid)),
                new KeyFrame(Duration.millis(3000), event -> shiftSymbols(reelsGrid))
        );
        timeline.play();
    }

    private void shiftSymbols(GridPane reelsGrid) {
        Symbol[][] board = spinBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Node node = getNodeByRowColumnIndex(i, j, reelsGrid);
                if (node instanceof ImageView imageView) {
                    Image image = board[i][j].image();
                    imageView.setImage(image);
                }
            }
        }
    }

    private Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        Node result = null;
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                result = node;
                break;

            }
        }
        return result;
    }


    public boolean checkWin(Symbol[][] symbols) {
        for (int i = 0; i < 3; i++) {
            if (symbols[i][0] != null && symbols[i][0].equals(symbols[i][1]) && symbols[i][1].equals(symbols[i][2])) {
                return true;
            }
        }
        if (symbols[0][0] != null && symbols[0][0].equals(symbols[1][1]) && symbols[1][1].equals(symbols[2][2])) {
            return true;
        } else
            return symbols[0][2] != null && symbols[0][2].equals(symbols[1][1]) && symbols[1][1].equals(symbols[2][0]);
    }


    public int calculateWin(int symbolId) {
        Symbol symbol = symbolService.getSymbol(symbolId);
        return symbol.winMultiplier() * stake;
    }

    public void updateBalance(int win) {
        balance += win;
    }

    public void deduct() {
        if (balance >= stake) {
            balance -= stake;
        } else {
            throw new IllegalArgumentException("Insufficient funds in the account.");
        }
    }

    public void deposit(int amount) {
        if (amount > 0) {
            balance = balance + amount;
        } else {
            throw new IllegalArgumentException("The top-up amount must be greater than zero.");
        }
    }

    public int getBalance() {
        return balance;
    }

    public int getStake() {
        return stake;
    }

    //tworzy widok symb
    public ImageView createSymbolImageView(Symbol symbol) {
        Image symbolImage = symbol.image();
        ImageView symbolImageView = new ImageView(symbolImage);
        symbolImageView.setFitWidth(250);
        symbolImageView.setPreserveRatio(true); //zachow proporcje symb
        return symbolImageView;
    }
}
