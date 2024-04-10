package com.example.magicfruitsgame.model;

import java.util.List;

public class Reel {
    private List<Symbol>symbols;

    public Reel(List<Symbol> symbols) {
        this.symbols = symbols;
    }

    public List<Symbol> getSymbols() {
        return symbols;
    }

    public void setSymbols(List<Symbol> symbols) {
        this.symbols = symbols;
    }
}
