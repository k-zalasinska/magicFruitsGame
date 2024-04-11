package com.example.magicfruitsgame.service;

import com.example.magicfruitsgame.model.Symbol;
import javafx.scene.image.Image;

import java.util.List;

public class SymbolService {
    private final List<Symbol> symbols = List.of(
            new Symbol(0, 5, new Image("symbol_cherry.png")),
            new Symbol(1, 10, new Image("symbol_plum.png")),
            new Symbol(2, 15, new Image("symbol_orange.png")),
            new Symbol(3, 20, new Image("symbol_pineapple.png")),
            new Symbol(4, 30, new Image("symbol_strawberry.png")),
            new Symbol(5, 100, new Image("symbol_watermelon.png")),
            new Symbol(6, 200, new Image("symbol_seven.png")));

    public List<Symbol> getSymbols() {
        return symbols;
    }

    public Symbol getSymbol(int id) {
        return symbols.stream()
                .filter(symbol -> symbol.id() == id)
                .findFirst()
                .orElse(null);
    }
}
