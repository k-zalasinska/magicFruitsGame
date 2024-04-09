package com.example.magicfruitsgame.service;

import com.example.magicfruitsgame.model.Symbol;

import java.util.ArrayList;
import java.util.List;

public class SymbolService {
    // Mapa przechowująca przyporządkowanie nazwy symbolu do liczby całkowitej
    private final List<Symbol> symbols = createSymbols();

    // Prywatny konstruktor, aby zapobiec tworzeniu instancji tej klasy
    private SymbolService() {
    }

    // Metoda statyczna tworząca symbole na podstawie indeksu
    public Symbol getSymbolByIndex(int index) {
        if (index >= 0 && index < symbols.size()) {
            return symbols.get(index);
        } else {
            // Obsługa przypadku, gdy podany indeks jest nieprawidłowy
            throw new IllegalArgumentException("Invalid index: " + index);
        }
    }

    // Metoda tworząca listę symboli zgodnie z założeniami gry
    private List<Symbol> createSymbols() {
        List<Symbol> symbols = new ArrayList<>();
        symbols.add(new Symbol("Cherry", 5, "com/example/magicfruitsgame/images/symbol_cherry.png"));
        symbols.add(new Symbol("Plum", 10, "com/example/magicfruitsgame/images/symbol_plum.png"));
        symbols.add(new Symbol("Orange", 15, "com/example/magicfruitsgame/images/symbol_orange.png"));
        symbols.add(new Symbol("Pineapple", 20, "com/example/magicfruitsgame/images/symbol_pineapple.png"));
        symbols.add(new Symbol("Strawberry", 30, "com/example/magicfruitsgame/images/symbol_strawberry.png"));
        symbols.add(new Symbol("Watermelon", 100, "com/example/magicfruitsgame/images/symbol_watermelon.png"));
        symbols.add(new Symbol("Seven (7)", 200, "com/example/magicfruitsgame/images/symbol_seven.png"));
        return symbols;
    }
}

