package com.example.magicfruitsgame.model;

public class Game {
    private int balance;
    private int stake;
    private int lastWin;
    private boolean gameActive;

    public Game() {
        this.balance = 1000; // Początkowe saldo gracza
        this.stake = 10; // Początkowa stawka
        this.lastWin = 0;
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

    public int getLastWin() {
        return lastWin;
    }

    public void setLastWin(int lastWin) {
        this.lastWin = lastWin;
    }

    public int getMultiplier(int symbol) {
        return switch (symbol) {
            case 0 -> 5;
            case 1 -> 10;
            case 2 -> 15;
            case 3 -> 20;
            case 4 -> 30;
            case 5 -> 100;
            case 6 -> 200;
            default -> 0;
        };
    }
}
