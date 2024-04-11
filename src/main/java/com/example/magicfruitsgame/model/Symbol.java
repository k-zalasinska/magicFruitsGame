package com.example.magicfruitsgame.model;

import javafx.scene.image.Image;

public class Symbol {
    private final int id;
    private final int winMultiplier;
    private final Image image;

    public Symbol(int id, int winMultiplier, Image image) {
        this.id = id;
        this.winMultiplier = winMultiplier;
        this.image = image;
    }

    public Symbol[] loadSymbols() {
        Symbol[] symbols = new Symbol[7];
        Image[] symbolImages = loadSymbolImages();

        symbols[0] = new Symbol(0, 5, symbolImages[0]);
        symbols[1] = new Symbol(1, 10, symbolImages[1]);
        symbols[2] = new Symbol(2, 15, symbolImages[2]);
        symbols[3] = new Symbol(3, 20, symbolImages[3]);
        symbols[4] = new Symbol(4, 30, symbolImages[4]);
        symbols[5] = new Symbol(5, 100, symbolImages[5]);
        symbols[6] = new Symbol(6, 200, symbolImages[6]);

        return symbols;
    }

    private Image[] loadSymbolImages() {
        Image[] symbolImages = new Image[7];
        symbolImages[0] = new Image("symbol_cherry.png");
        symbolImages[1] = new Image("symbol_plum.png");
        symbolImages[2] = new Image("symbol_orange.png");
        symbolImages[3] = new Image("symbol_pineapple.png");
        symbolImages[4] = new Image("symbol_strawberry.png");
        symbolImages[5] = new Image("symbol_watermelon.png");
        symbolImages[6] = new Image("symbol_seven.png");

        return symbolImages;
    }

    public int getId() {
        return id;
    }

    public int getWinMultiplier() {
        return winMultiplier;
    }

    public Image getImage() {
        return image;
    }
}
