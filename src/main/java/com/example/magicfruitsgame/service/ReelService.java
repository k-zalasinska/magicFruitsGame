package com.example.magicfruitsgame.service;

import java.util.Random;

//Klasa Reel jest odpowiedzialna za reprezentację pojedynczego bębna w grze.
public class ReelService {

    private final int reelIndex; //przechow. indeks bębna, który jest potrz. do pobr. odpowiedniej def. bębna z dwuwym. tabl reelsDefinition.
    private final int[][] reelsDefinition;

    public ReelService(int reelIndex, int[][] reelsDefinition) {
        this.reelIndex = reelIndex;
        this.reelsDefinition = reelsDefinition;
    }

    // Metoda losująca symbole na bębnie
    public int[] spin() { //metoda jest odpowiedz. za losowanie symboli na bębnie na podstawie zdefiniow. tablicy reelsDefinition.
        Random random = new Random();
        int drawnIndex = random.nextInt(reelsDefinition[reelIndex].length - 2); // -2, aby uniknąć wyjścia poza zakres

        int[] symbols = new int[3]; // Tablica na trzy symbole na bębnie
        symbols[0] = reelsDefinition[reelIndex][drawnIndex];
        symbols[1] = reelsDefinition[reelIndex][drawnIndex + 1];
        symbols[2] = reelsDefinition[reelIndex][drawnIndex + 2];

        return symbols;
    }

    public int getCurrentSymbol() { //losuje pojedynczy symbol z bębna, aby uzyskać aktualny symbol, który jest wyświetlany na ekranie
        Random random = new Random();
        int drawnIndex = random.nextInt(reelsDefinition[reelIndex].length);
        return reelsDefinition[reelIndex][drawnIndex];
    }
}
