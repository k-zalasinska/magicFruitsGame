package com.example.magicfruitsgame.service;

import com.example.magicfruitsgame.model.Player;

public class PlayerService {
    private final Player player;

    public PlayerService(Player player) {
        this.player = player;
    }


    // Metoda doładowująca stan konta
    public void deposit(int amount) {
        if (amount > 0) {
            int currentBalance = player.getBalance();
            player.setBalance(currentBalance + amount);
        } else {
            throw new IllegalArgumentException("The top-up amount must be greater than zero.");
        }
    }


    // Metoda pobierająca stan konta
    public int getBalance() {
        return player.getBalance();
    }

    // Metoda odjęcia stawki od stanu konta za rozgrywkę
    public void deduct() {
        int currentBalance = player.getBalance();
        if (currentBalance >= 10) {
            player.setBalance(currentBalance - 10);
        } else {
            throw new IllegalArgumentException("Insufficient funds in the account.");
        }
    }

    // Metoda dodająca wygraną z rozgrywki do salda gracza
    public void addWinnings(int winnings) {
        if (winnings <= 0) {
            throw new IllegalArgumentException("Winning amount must be greater than zero.");
        }
        int currentBalance = player.getBalance();
        player.setBalance(currentBalance + winnings);
    }

    // Metoda doładowująca saldo konta gracza
    public void topUpBalance(int topUpAmount) {
        if (topUpAmount <= 0) {
            throw new IllegalArgumentException("Top-up amount must be greater than zero.");
        }
        int currentBalance = player.getBalance();
        player.setBalance(currentBalance + topUpAmount);
    }
}
