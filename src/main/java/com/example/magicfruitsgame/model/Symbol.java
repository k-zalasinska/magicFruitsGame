package com.example.magicfruitsgame.model;

import javafx.scene.image.Image;

/**
 * Represents a symbol in the slot machine game.
 * Each symbol has an identifier, a win multiplier, and an associated image.
 */
public record Symbol(int id, int winMultiplier, Image image) {
}
