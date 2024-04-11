package com.example.magicfruitsgame.service;

import com.example.magicfruitsgame.model.Game;
import com.example.magicfruitsgame.model.Symbol;

public class GameService {
    private final Game game;

    public GameService(Game game) {
        this.game = game;
    }

    public Symbol[] getSymbols() {
        return game.getSymbols();
    }
}
