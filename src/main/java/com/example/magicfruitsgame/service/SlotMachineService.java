package com.example.magicfruitsgame.service;

import com.example.magicfruitsgame.model.Symbol;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import static com.example.magicfruitsgame.service.AlertHelper.showWinAlert;

/**
 * Service class responsible for managing the slot machine functionality.
 */
public class SlotMachineService {
    private final GameService gameService;
    private Symbol[][] currentBoard;

    /**
     * Constructs a new instance of SlotMachineService.
     *
     * @param gameService The game service instance.
     */
    public SlotMachineService(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Starts the animation of the slot machine reels.
     *
     * @param reelsGrid The GridPane representing the reels.
     */
    public void startAnimation(GridPane reelsGrid) {
        currentBoard = gameService.spinBoard();

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(100), event -> {
                    Symbol[][] updatedBoard = shiftSymbolsDown(currentBoard);
                    updateReels(reelsGrid, updatedBoard);
                    currentBoard = updatedBoard;
                })
        );
        timeline.setCycleCount(30);
        timeline.setOnFinished(event -> checkWinAndUpdate(currentBoard));
        timeline.play();
    }

    /**
     * Shifts the symbols down in the given board.
     *
     * @param board The board containing symbols.
     * @return The updated board with shifted symbols.
     */
    private Symbol[][] shiftSymbolsDown(Symbol[][] board) {
        Symbol[][] updatedBoard = new Symbol[3][3];

        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 3; row++) {
                int newRow = (row + 1) % 3;
                updatedBoard[newRow][col] = board[row][col];
            }
        }
        return updatedBoard;
    }

    /**
     * Updates the reels grid with the symbols from the given board.
     *
     * @param reelsGrid The GridPane representing the reels.
     * @param board     The board containing symbols.
     */
    private void updateReels(GridPane reelsGrid, Symbol[][] board) {
        reelsGrid.getChildren().clear();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Symbol symbol = board[i][j];
                if (symbol != null) {
                    ImageView symbolImageView = createSymbolImageView(symbol);
                    reelsGrid.add(symbolImageView, j, i);
                    symbolImageView.toFront();
                }
            }
        }
    }

    /**
     * Creates an ImageView for the given symbol.
     *
     * @param symbol The symbol for which to create an ImageView.
     * @return The created ImageView.
     */
    private ImageView createSymbolImageView(Symbol symbol) {
        Image symbolImage = symbol.image();
        ImageView symbolImageView = new ImageView(symbolImage);
        symbolImageView.setFitWidth(250);
        symbolImageView.setPreserveRatio(true);
        return symbolImageView;
    }

    /**
     * Checks for a win on the board, updates the balance, and shows an alert if a win occurs.
     *
     * @param board The board containing symbols.
     */
    private void checkWinAndUpdate(Symbol[][] board) {
        gameService.checkAndCalculateWin(board);
        int winAmount = gameService.getLastWin();
        if (winAmount > 0) {
            gameService.updateBalance(winAmount);
            showWinAlert(winAmount);
        }

    }

}
