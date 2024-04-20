package com.example.magicfruitsgame.service;

import com.example.magicfruitsgame.model.Symbol;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
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

    /**
     * Constructs a new instance of SlotMachineService.
     *
     * @param gameService the game service instance
     */
    public SlotMachineService(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Creates an ImageView for the provided symbol.
     *
     * @param symbol the symbol for which to create an ImageView
     * @return the ImageView displaying the symbol's image
     */
    public ImageView createSymbolImageView(Symbol symbol) {
        Image symbolImage = symbol.image();
        ImageView symbolImageView = new ImageView(symbolImage);
        symbolImageView.setFitWidth(250);
        symbolImageView.setPreserveRatio(true);
        return symbolImageView;
    }

    /**
     * Animates the spinning of the slot machine reels.
     *
     * @param reelsGrid the GridPane representing the reels
     */
    public void animateSpin(GridPane reelsGrid) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(1000), event -> shiftSymbols(reelsGrid)),
                new KeyFrame(Duration.millis(2000), event -> shiftSymbols(reelsGrid)),
                new KeyFrame(Duration.millis(3000), event -> shiftSymbols(reelsGrid))
        );
        timeline.play();
    }

    /**
     * Shifts the symbols on the reels to create a spinning animation effect.
     *
     * @param reelsGrid the GridPane representing the reels
     */
    private void shiftSymbols(GridPane reelsGrid) {
        Symbol[][] board = gameService.spinBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Node node = getNodeByRowColumnIndex(i, j, reelsGrid);
                if (node instanceof ImageView imageView) {
                    Image image = board[i][j].image();
                    imageView.setImage(image);
                }
            }
        }
    }

    /**
     * Retrieves the node from the GridPane at the specified row and column indices.
     *
     * @param row      the row index
     * @param column   the column index
     * @param gridPane the GridPane
     * @return the node at the specified position, or null if not found
     */
    private Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        Node result = null;
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                result = node;
                break;

            }
        }
        return result;
    }

    /**
     * Initiates the spinning animation of the slot machine reels.
     *
     * @param reelsGrid the GridPane representing the reels
     */
    public void startSpinAnimation(GridPane reelsGrid) {
        animateSpin(reelsGrid);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(3000), event -> {
            Symbol[][] symbols = gameService.spinBoard();
            reelsGrid.getChildren().clear();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    Symbol symbol = symbols[i][j];
                    if (symbol != null) {
                        ImageView symbolImageView = createSymbolImageView(symbol);
                        reelsGrid.add(symbolImageView, j, i);
                        symbolImageView.toFront();
                    }
                }
            }

            if (gameService.checkWin(symbols)) {
                int winAmount = gameService.calculateWin(symbols[1][0].id());
                gameService.updateBalance(winAmount);
                showWinAlert(winAmount);
            }
        }));
        timeline.play();
    }

}
