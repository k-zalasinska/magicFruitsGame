package com.example.magicfruitsgame.service;

import java.util.Random;

public class ReelsDefinitionService {
    private static final int REEL_COUNT = 3;
    private static final int SYMBOL_COUNT = 3;

    // Metoda tworząca definicje bębnów
    public static int[][] createReelsDefinition() {
        Random random = new Random();
        int[][] reelsDefinition = new int[REEL_COUNT][SYMBOL_COUNT];

        // Generowanie definicji bębnów
        for (int i = 0; i < REEL_COUNT; i++) {
            for (int j = 0; j < SYMBOL_COUNT; j++) {
                reelsDefinition[i][j] = random.nextInt(SymbolService.createSymbols().length);
            }
        }
        return reelsDefinition;
    }
}
