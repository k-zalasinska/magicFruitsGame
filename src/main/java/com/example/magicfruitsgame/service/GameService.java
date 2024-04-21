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
    private int lastWin;

    /**
     * Constructs a new GameService with the specified SymbolService.
     *
     * @param symbolService The SymbolService to use for retrieving symbols.
     */
    public GameService(SymbolService symbolService) {
        this.symbolService = symbolService;
    }

    /**
     * Spins the game board and returns the resulting symbols.
     *
     * @return a 2D array representing the game board with symbols.
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
     * Checks for winning combinations on the given board of symbols and calculates the total win amount.
     * Winning combinations are checked horizontally (across rows) and diagonally.
     *
     * @param symbols The board containing symbols.
     */
    public void checkAndCalculateWin(Symbol[][] symbols) {

        for (int i = 0; i < 3; i++) {
            if (symbols[i][0] != null && symbols[i][0].equals(symbols[i][1]) && symbols[i][1].equals(symbols[i][2])) {
                lastWin += symbols[i][0].winMultiplier() * stake;
            }
        }
        if (symbols[0][0] != null && symbols[0][0].equals(symbols[1][1]) && symbols[1][1].equals(symbols[2][2])) {
            lastWin += symbols[0][0].winMultiplier() * stake;
        }
        if (symbols[0][2] != null && symbols[0][2].equals(symbols[1][1]) && symbols[1][1].equals(symbols[2][0])) {
            lastWin += symbols[0][2].winMultiplier() * stake;
        }
    }


    /**
     * Updates the player's balance with the specified win amount.
     *
     * @param lastWin The amount won.
     */
    public void updateBalance(int lastWin) {
        balance += lastWin;
    }

    /**
     * Deducts the stake amount from the player's balance.
     *
     * @throws IllegalArgumentException if the balance is insufficient.
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
     * @param amount The amount to deposit.
     * @throws IllegalArgumentException if the amount is non-positive.
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
     * @return the balance.
     */
    public int getBalance() {
        return balance;
    }

    /**
     * Gets the stake amount for each game round.
     *
     * @return the stake amount.
     */
    public int getStake() {
        return stake;
    }


    public int getLastWin() {
        return lastWin;
    }
}
