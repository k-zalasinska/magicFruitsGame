package com.example.magicfruitsgame.service;

import java.util.Random;

public class ReelService {
    private final int[][] reelsDefinition;

    public ReelService(int[][] reelsDefinition) {
        this.reelsDefinition = reelsDefinition;
    }


    public int[] spin(int reelIndex) {
        int[] reelSymbols = new int[3];
        Random random = new Random();

        // Losujemy indeks początkowy w celu rozpoczęcia losowania symboli z walców
        int startIndex = random.nextInt(reelsDefinition[reelIndex].length);

        // Losujemy symbole z walców, zaczynając od losowego indeksu
        for (int i = 0; i < 3; i++) {
            int index = (startIndex + i) % reelsDefinition[reelIndex].length;
            reelSymbols[i] = reelsDefinition[reelIndex][index];
        }

        return reelSymbols;
    }
}
