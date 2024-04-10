package com.example.magicfruitsgame.model;

public class Game {
    private int balance;
    private int stake;
    private int lastWin;

    public Game() {
        this.balance = 1000;
        this.stake = 10;
        this.lastWin = 0;
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
}
