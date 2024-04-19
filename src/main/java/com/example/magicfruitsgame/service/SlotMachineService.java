package com.example.magicfruitsgame.service;

import com.example.magicfruitsgame.model.Symbol;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import static com.example.magicfruitsgame.service.AlertHelper.showWinAlert;

public class SlotMachineService {
    private final GameService gameService;

    public SlotMachineService(GameService gameService) {
        this.gameService = gameService;
    }

    public void startSpinAnimation(ImageView[] reel1, ImageView[] reel2, ImageView[] reel3) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, event -> spinReel(reel1)),
                new KeyFrame(Duration.seconds(1), event -> spinReel(reel2)),
                new KeyFrame(Duration.seconds(2), event -> spinReel(reel3))
        );

        timeline.setOnFinished(event -> {
            Symbol[][] symbols = gameService.spinBoard();
            if (gameService.checkWin(symbols)) {
                int lastWin = gameService.calculateWin(symbols[1][0].id());
                gameService.updateBalance(lastWin);
                showWinAlert(lastWin);
            }
        });

        timeline.play();
    }

    private void spinReel(ImageView[] reels) {
        ParallelTransition parallelTransition = new ParallelTransition();
        for (int i = 0; i < reels.length; i++) {
            Symbol newSymbol = gameService.spinBoard()[i][0];
            ImageView imageView = reels[i];
            KeyValue keyValue = new KeyValue(imageView.imageProperty(), newSymbol.image());
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), keyValue);
            parallelTransition.getChildren().add(new Timeline(keyFrame));
        }
        parallelTransition.play();
    }

}
