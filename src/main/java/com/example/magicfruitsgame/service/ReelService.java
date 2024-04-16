package com.example.magicfruitsgame.service;

import com.example.magicfruitsgame.model.Symbol;

import java.util.List;
import java.util.Random;

public class ReelService {
    private final Random random;
    private final SymbolService symbolService;

    public ReelService(SymbolService symbolService) {
        this.random = new Random();
        this.symbolService = symbolService;
    }

    public int[] spin() {
        List<Symbol> symbols = symbolService.getSymbols();

        if (symbols.isEmpty()) {
            throw new IllegalStateException("No symbols available for spinning.");
        }

        int[] reelSymbols = new int[3];
        for (int i = 0; i < 3; i++) {
            int symbolIndex = random.nextInt(symbols.size());
            reelSymbols[i] = symbols.get(symbolIndex).id();
        }

        return reelSymbols;
    }
}
