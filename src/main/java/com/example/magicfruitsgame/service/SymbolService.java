package com.example.magicfruitsgame.service;

import com.example.magicfruitsgame.model.Symbol;
import javafx.scene.image.Image;

public class SymbolService {
    private final Symbol[] symbols;

    private SymbolService(Symbol[] symbols) {
        this.symbols = symbols;
    }

    public static Symbol[] createSymbols() {
        Symbol[] symbols = new Symbol[7];

        symbols[0] = new Symbol(0, new Image("symbol_cherry.png"), 5);
        symbols[1] = new Symbol(1, new Image("symbol_plum.png"), 10);
        symbols[2] = new Symbol(2, new Image("symbol_orange.png"), 15);
        symbols[3] = new Symbol(3, new Image("symbol_pineapple.png"), 20);
        symbols[4] = new Symbol(4, new Image("symbol_strawberry.png"), 30);
        symbols[5] = new Symbol(5, new Image("symbol_watermelon.png"), 100);
        symbols[6] = new Symbol(6, new Image("symbol_seven.png"), 200);

        return symbols;
    }

    public int getSymbolByIndex(int index) {
        if (index >= 0 && index < symbols.length) {
            return index;
        } else {
            throw new IllegalArgumentException("Invalid index: " + index);
        }
    }
}
