package com.example.magicfruitsgame.model;

import javafx.scene.image.Image;

public class Symbol {
    private final int id;
    private final Image image;
    private final int winMultiplier;


    public Symbol(int id, Image image, int winMultiplier) {
        this.id = id;
        this.image = image;
        this.winMultiplier = winMultiplier;
    }

    public int getId() {
        return id;
    }

    public Image getImage() {
        return image;
    }

    public int getWinMultiplier() {
        return winMultiplier;
    }
}
