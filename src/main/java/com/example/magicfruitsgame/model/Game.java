package com.example.magicfruitsgame.model;

public class Game {
    private int balance;
    private final int stake;
    private int lastWin;
    private boolean running;

    public Game() {
        this.balance = 1000;
        this.stake = 10;
        this.lastWin = 0;
        this.running = false;
    }

    public boolean isGameRunning() {
        return running;
    }

    public void startGame() {
        running = true;
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

    public int getLastWin() {
        return lastWin;
    }

    public void setLastWin(int lastWin) {
        this.lastWin = lastWin;
    }
}
