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
     * @param gameService the game service instance
     */
    public SlotMachineService(GameService gameService) {
        this.gameService = gameService;
    }

    public void startAnimation(GridPane reelsGrid) {
        currentBoard = gameService.spinBoard(); // Inicjalizacja planszy symboli

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(100), event -> {
                    Symbol[][] updatedBoard = shiftSymbolsDown(currentBoard);
                    updateReels(reelsGrid, updatedBoard);
                    currentBoard = updatedBoard; // Zaktualizowanie referencji planszy
                })
        );
        timeline.setCycleCount(30);
        timeline.setOnFinished(event -> {
            checkWinAndUpdate(reelsGrid, currentBoard);
        });
        timeline.play();
    }


    // Metoda do przesunięcia symboli w dół na planszy
    private Symbol[][] shiftSymbolsDown(Symbol[][] board) {
        Symbol[][] updatedBoard = new Symbol[3][3];
        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 3; row++) {
                // Przesunięcie symbolu w dół, z górnego rzędu na środkowy, ze środkowego na dolny
                int newRow = (row + 1) % 3;
                updatedBoard[newRow][col] = board[row][col];
            }
        }
        return updatedBoard;
    }


    private void updateReels(GridPane reelsGrid, Symbol[][] board) {
        reelsGrid.getChildren().clear();
        // Dodanie nowych symboli na planszę
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

    private ImageView createSymbolImageView(Symbol symbol) {
        Image symbolImage = symbol.image();
        ImageView symbolImageView = new ImageView(symbolImage);
        symbolImageView.setFitWidth(250);
        symbolImageView.setPreserveRatio(true);
        return symbolImageView;
    }

    private void checkWinAndUpdate(GridPane reelsGrid, Symbol[][] board) {
        if (gameService.checkWin(board)) {
            int winAmount = gameService.calculateWin(board);
            gameService.updateBalance(winAmount);
            showWinAlert(winAmount);
        }
    }

}
