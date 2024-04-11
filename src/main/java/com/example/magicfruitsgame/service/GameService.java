package com.example.magicfruitsgame.service;

import com.example.magicfruitsgame.model.Symbol;

public class GameService {
    private final Symbol[] symbols;

    public GameService(Symbol[] symbols) {
        this.symbols = symbols;
    }

    public Symbol[] getSymbols() {
        return symbols;
    }
}
