package com.example.magicfruitsgame.service;

import com.example.magicfruitsgame.model.Game;

public class GameService {
    private final Game game;

    public GameService(Game game) {
        this.game = game;
    }


    // Metoda doładowująca stan konta
    public void deposit(int amount) {
        if (amount > 0) {
            int currentBalance = game.getBalance();
            game.setBalance(currentBalance + amount);
        } else {
            throw new IllegalArgumentException("The top-up amount must be greater than zero.");
        }
    }


    // Metoda pobierająca stan konta
    public int getBalance() {
        return game.getBalance();
    }

    // Metoda odjęcia stawki od stanu konta za rozgrywkę
    public void deduct() {
        int currentBalance = game.getBalance();
        if (currentBalance >= 10) {
            game.setBalance(currentBalance - 10);
        } else {
            throw new IllegalArgumentException("Insufficient funds in the account.");
        }
    }

    // Metoda dodająca wygraną z rozgrywki do salda gracza
    public void addWinnings(int winnings) {
        if (winnings <= 0) {
            throw new IllegalArgumentException("Winning amount must be greater than zero.");
        }
        int currentBalance = game.getBalance();
        game.setBalance(currentBalance + winnings);
    }

    // Metoda doładowująca saldo konta gracza
    public void topUpBalance(int topUpAmount) {
        if (topUpAmount <= 0) {
            throw new IllegalArgumentException("Top-up amount must be greater than zero.");
        }
        int currentBalance = game.getBalance();
        game.setBalance(currentBalance + topUpAmount);
    }
}
