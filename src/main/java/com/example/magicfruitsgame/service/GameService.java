//package com.example.magicfruitsgame.service;
//
//import java.util.List;
//
//public class GameService {
//    private final List<ReelService> reels;
//    private final PlayerService player;
//    private final int stake;
//
//    public GameService(List<ReelService> reels, PlayerService player) {
//        this.reels = reels;
//        this.player = player;
//        this.stake = 10;
//    }
//
//    public void startNewRound() {
//        if (player.getBalance() < stake) {
//            System.out.println("Insufficient balance. Please refill your balance.");
//            return;
//        }
//        spinReels();
//        calculateWin();
//    }
//
//    private void spinReels() {
//        for (ReelService reel : reels) {
//            reel.spin();
//        }
//    }
//
//    private void calculateWin() {
//        int[] symbols = new int[3];
//        for (int i = 0; i < reels.size(); i++) {
//            symbols[i] = reels.get(i).getCurrentSymbol();
//        }
//
//        // Sprawdzanie, czy symbole na środkowej linii są takie same
//        if (symbols[0] == symbols[1] && symbols[1] == symbols[2]) {
//            int multiplier = getMultiplier(symbols[0]);
//            int winAmount = stake * multiplier;
//            player.addWinnings(winAmount);
//        }
//    }
//
//
//    private int getMultiplier(int symbol) {
//        return switch (symbol) {
//            case 0 -> 5;
//            case 1 -> 10;
//            case 2 -> 15;
//            case 3 -> 20;
//            case 4 -> 30;
//            case 5 -> 100;
//            case 6 -> 200;
//            default -> 0;
//        };
//    }
//
//    public int getBalance() {
//        return player.getBalance();
//    }
//
//    public int getStake() {
//        return stake;
//    }
//}
