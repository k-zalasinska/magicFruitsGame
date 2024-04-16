package com.example.magicfruitsgame.service;

import com.example.magicfruitsgame.model.Symbol;

import java.util.List;
import java.util.Random;

public class ReelService {
    private final Random random;
    final SymbolService symbolService;

    public ReelService(SymbolService symbolService) {
        this.random = new Random();
        this.symbolService = symbolService;
    }

    public Symbol[] spin() {
        List<Symbol> symbols = symbolService.getSymbols();

        if (symbols.isEmpty()) {
            throw new IllegalStateException("No symbols available for spinning.");
        }

        Symbol[] reelSymbols = new Symbol[3];
        for (int i = 0; i < 3; i++) {
            int symbolIndex = random.nextInt(symbols.size());
            reelSymbols[i] = symbols.get(symbolIndex);
        }

        return reelSymbols;
    }
}
