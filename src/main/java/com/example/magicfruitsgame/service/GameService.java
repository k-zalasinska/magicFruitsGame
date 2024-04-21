package com.example.magicfruitsgame.service;

import com.example.magicfruitsgame.model.Symbol;

import java.util.List;
import java.util.Random;

/**
 * Service class responsible for managing the game logic and state.
 */
public class GameService {
    private static final Random random = new Random();
    private final SymbolService symbolService;
    private final int stake = 10;
    private int balance = 1000;
    private int lastWin = 0;

    /**
     * Constructs a new GameService with the specified SymbolService.
     *
     * @param symbolService the SymbolService to use for retrieving symbols
     */
    public GameService(SymbolService symbolService) {
        this.symbolService = symbolService;
    }

    /**
     * Spins the game board and returns the resulting symbols.
     *
     * @return a 2D array representing the game board with symbols
     */
    public Symbol[][] spinBoard() {
        List<Symbol> symbols = symbolService.getSymbols();
        Symbol[][] board = new Symbol[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Symbol randomSymbol = symbols.get(random.nextInt(symbols.size()));
                board[i][j] = randomSymbol;
            }
        }
        return board;
    }

    /**
     * Checks if there is a winning combination of symbols on the game board.
     *
     * @param symbols the symbols on the game board
     * @return true if there is a winning combination, false otherwise
     */
    public boolean checkWin(Symbol[][] symbols) {
        for (int i = 0; i < 3; i++) {
            if (symbols[i][0] != null && symbols[i][0].equals(symbols[i][1]) && symbols[i][1].equals(symbols[i][2])) {
                return true;
            }
        }
        if (symbols[0][0] != null && symbols[0][0].equals(symbols[1][1]) && symbols[1][1].equals(symbols[2][2])) {
            return true;
        } else
            return symbols[0][2] != null && symbols[0][2].equals(symbols[1][1]) && symbols[1][1].equals(symbols[2][0]);
    }

//    /**
//     * Calculates the win amount based on the multiplier of the symbol.
//     *
//     * @param symbolId the ID of the winning symbol
//     */
//    public int calculateWin(int symbolId) {
//        Symbol symbol = symbolService.getSymbol(symbolId);
//        return symbol.winMultiplier() * stake;
//    }

    public int calculateWin(Symbol[][] symbols) {
        int winAmount = 0;

        // Sprawdzamy wygraną dla każdej linii poziomej
        for (int i = 0; i < 3; i++) {
            if (symbols[i][0] != null && symbols[i][0].equals(symbols[i][1]) && symbols[i][1].equals(symbols[i][2])) {
                winAmount += symbols[i][0].winMultiplier() * stake;
            }
        }

        // Sprawdzamy wygraną dla linii na ukos (przekątnych)
        if (symbols[0][0] != null && symbols[0][0].equals(symbols[1][1]) && symbols[1][1].equals(symbols[2][2])) {
            winAmount += symbols[0][0].winMultiplier() * stake;
        }
        if (symbols[0][2] != null && symbols[0][2].equals(symbols[1][1]) && symbols[1][1].equals(symbols[2][0])) {
            winAmount += symbols[0][2].winMultiplier() * stake;
        }

        return winAmount;
    }


    /**
     * Updates the player's balance with the specified win amount.
     *
     * @param win the amount won
     */
    public void updateBalance(int win) {
        balance += win;
    }

    /**
     * Deducts the stake amount from the player's balance.
     *
     * @throws IllegalArgumentException if the balance is insufficient
     */
    public void deduct() {
        if (balance >= stake) {
            balance -= stake;
        } else {
            throw new IllegalArgumentException("Insufficient funds in the account.");
        }
    }

    /**
     * Deposits the specified amount into the player's balance.
     *
     * @param amount the amount to deposit
     * @throws IllegalArgumentException if the amount is non-positive
     */
    public void deposit(int amount) {
        if (amount > 0) {
            balance = balance + amount;
        } else {
            throw new IllegalArgumentException("The top-up amount must be greater than zero.");
        }
    }

    /**
     * Gets the current balance of the player.
     *
     * @return the balance
     */
    public int getBalance() {
        return balance;
    }

    /**
     * Gets the stake amount for each game round.
     *
     * @return the stake amount
     */
    public int getStake() {
        return stake;
    }

    public int getLastWin() {
        return lastWin;
    }

    public void updateLastWin(int winAmount) {
        this.lastWin = winAmount;
    }


}
