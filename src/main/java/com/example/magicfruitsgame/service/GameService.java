package com.example.magicfruitsgame.service;

import com.example.magicfruitsgame.model.Game;
import com.example.magicfruitsgame.model.Symbol;

public class GameService {
    private final ReelService reelService;
    private final SymbolService symbolService;
    private final Game game;
    private final int[][] board;

    public GameService(ReelService reelService, SymbolService symbolService, Game game) {
        this.reelService = reelService;
        this.symbolService = symbolService;
        this.game = game;
        this.board = new int[3][3];
    }

    public int[][] spinBoard() {
        if (!game.isGameRunning()) {
            throw new IllegalStateException("Game is not running.");
        }
        for (int i = 0; i < 3; i++) {
            board[i] = reelService.spin();
        }
        if (checkWin()) {
            int win = calculateWin(board[1][0]);
            updateBalance(win);
            game.setLastWin(win);
        } else {
            game.setLastWin(0);
        }
        return board;
    }

    public boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            int symbol = board[i][1]; //sprawdza środk rząd każdego bębna
            if (board[i][0] != symbol || board[i][2] != symbol) {
                return false;
            }
        }
        return true;
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

    public void startGame() {
        game.startGame();
    }

    public Game getGame() {
        return game;
    }

    public boolean isGameRunning() {
        return game.isGameRunning();
    }

    // Metoda odjęcia stawki od stanu konta za rozgrywkę
    public void deduct() {
        int currentBalance = game.getBalance();
        int stake = game.getStake();
        if (currentBalance >= stake) {
            game.setBalance(currentBalance - stake);
        } else {
            throw new IllegalArgumentException("Insufficient funds in the account.");
        }
    }
}
