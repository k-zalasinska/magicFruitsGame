package com.example.magicfruitsgame.service;

import com.example.magicfruitsgame.model.Symbol;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;
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
        for (int i = 0; i < 3; i++) {
            List<Symbol> symbols = symbolService.getSymbols();

            Symbol[] reelSymbols = new Symbol[3];
            for (int j = 0; j < 3; j++) {
                int symbolIndex = random.nextInt(symbols.size());
                reelSymbols[j] = symbols.get(symbolIndex);
            }

            board[i] = reelSymbols;
        }

        return board;
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
