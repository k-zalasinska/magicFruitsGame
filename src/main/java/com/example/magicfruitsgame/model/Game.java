package com.example.magicfruitsgame.model;

public class Game {
    private int balance;
    private int stake;
    private boolean gameActive;

    public Game() {
        this.balance = 1000; // Początkowe saldo gracza
        this.stake = 10; // Początkowa stawka
        this.gameActive = false;
    }

    public boolean isGameActive() {
        return gameActive;
    }

    public void startGame() {
        gameActive = true;
    }

    public void endGame() {
        gameActive = false;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getStake() {
        return stake;
    }

    public void setStake(int stake) {
        this.stake = stake;
    }


}
