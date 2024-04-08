package com.example.magicfruitsgame.model;

public class Player {
    private int balance;

    public Player() {
        this.balance = 1000;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    // Metoda dodająca wygraną do salda gracza
    public void addWinnings(int winnings) {
        if (winnings < 0) {
            throw new IllegalArgumentException("Winnings amount must be non-negative.");
        }
        balance += winnings;
    }
}

