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
    private final GridPane reelsGrid;

    /**
     * Constructs a new instance of SlotMachineService.
     *
     * @param gameService the game service instance
     */
    public SlotMachineService(GameService gameService, GridPane reelsGrid) {
        this.gameService = gameService;
        this.reelsGrid = reelsGrid;
    }

    public void startAnimation() {
        Symbol[][] board = gameService.spinBoard(); // Inicjalizacja planszy symboli

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(100), event -> {
                    Symbol[][] updatedBoard = shiftSymbolsDown(board);
                    updateReels(updatedBoard);
                })
        );
        timeline.setCycleCount(30);
        timeline.setOnFinished(event -> {
            Symbol[][] finalBoard = gameService.spinBoard();
            if (gameService.checkWin(finalBoard)) {
                int symbolId = finalBoard[1][1].id();
                int winAmount = gameService.calculateWin(symbolId);
                gameService.updateBalance(winAmount);
                showWinAlert(winAmount);
            }
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


    private void updateReels(Symbol[][] board) {
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

}
