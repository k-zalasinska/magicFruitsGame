package com.example.magicfruitsgame.service;

import com.example.magicfruitsgame.model.Game;
import com.example.magicfruitsgame.model.Symbol;

import java.util.List;
import java.util.Random;

public class GameService {
    private static final Random random = new Random();
    private final SymbolService symbolService;
    private final Game game = new Game();

    public GameService(SymbolService symbolService) {
        this.symbolService = symbolService;
    }

    public void startGame() {
        game.setBalance(1000);
        game.setLastWin(0);
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
            if (symbols[i][0] != null && symbols[i][0] == symbols[i][1] && symbols[i][1] == symbols[i][2]) { //symb w rzędach
                return true;
            }
        }
        if (symbols[0][0] != null && symbols[0][0] == symbols[1][1] && symbols[1][1] == symbols[2][2]) {
            return true;
        } else return symbols[0][2] != null && symbols[0][2] == symbols[1][1] && symbols[1][1] == symbols[2][0];
    }

    public int calculateWin(int symbolId) {
        Symbol symbol = symbolService.getSymbol(symbolId);
        return symbol.winMultiplier() * game.getStake();
    }

    public void updateBalance(int win) {
        int currentBalance = game.getBalance();
        int newBalance = currentBalance + win;
        game.setBalance(newBalance);
    }

    public void deduct() {
        int currentBalance = game.getBalance();
        int stake = game.getStake();
        if (currentBalance >= stake) {
            game.setBalance(currentBalance - stake);
        } else {
            throw new IllegalArgumentException("Insufficient funds in the account.");
        }
    }

    public void deposit(int amount) {
        if (amount > 0) {
            game.setBalance(game.getBalance() + amount);
        } else {
            throw new IllegalArgumentException("The top-up amount must be greater than zero.");
        }
    }

    public int getBalance() {
        return game.getBalance();
    }

    public int getStake() {
        return game.getStake();
    }

    public int getLastWin() {
        return game.getLastWin();
    }

}
