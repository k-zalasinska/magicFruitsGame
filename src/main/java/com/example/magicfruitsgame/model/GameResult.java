package com.example.magicfruitsgame.model;

public class GameResult {
    private final int winAmount;
    private final Player player;

    public GameResult(int winAmount, Player player) {
        this.winAmount = winAmount;
        this.player = player;
    }

    public int getWinAmount() {
        return winAmount;
    }

    public int getPlayerBalance() {
        return player.getBalance();
    }
}
