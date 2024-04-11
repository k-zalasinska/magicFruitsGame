package com.example.magicfruitsgame.model;

public class Game {
    private int balance;
    private final int stake;
    private boolean running;

    public Game() {
        this.balance = 1000; // Początkowe saldo gracza
        this.stake = 10; // Początkowa stawka
        this.running = false;
    }

    public boolean isGameRunning() {
        return running;
    }

    public void startGame() {
        running = true;
    }

    public void endGame() {
        running = false;
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


}
