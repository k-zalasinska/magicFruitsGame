package com.example.magicfruitsgame.service;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

public class SlotMachineGame extends Application {
    private ReelService reelService;

    private static final int SYMBOL_HEIGHT = 100; // Wysokość symbolu
    private static final int REEL_COUNT = 3; // Liczba walców
    private static final int ANIMATION_DURATION = 3000; // Czas trwania animacji w milisekundach

    // Symbole na walcach
    private Image[][] reels;

    // Kontenery na symbole na walcach
    private ImageView[][] symbols;

    @Override
    public void start(Stage primaryStage) {
        // Inicjalizacja symboli na walcach
        initializeReels();

        // Tworzenie kontenera na walcach
        StackPane root = new StackPane();
        root.getChildren().addAll(symbols[0][0], symbols[1][0], symbols[2][0]);

        // Tworzenie sceny i dodanie kontenera do niej
        Scene scene = new Scene(root, 150, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Slot Machine Game");
        primaryStage.show();

        // Rozpoczęcie animacji losowania symboli
        startSpinAnimation();
    }

    private void initializeReels() {
        // Inicjalizacja symboli na walcach
        reels = new Image[REEL_COUNT][];
        reels[0] = new Image[]{new Image(Objects.requireNonNull(getClass().getResourceAsStream("com/example/magicfruitsgame/images/symbol_cherry.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("com/example/magicfruitsgame/images/symbol_plum.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("com/example/magicfruitsgame/images/symbol_orange.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("com/example/magicfruitsgame/images/symbol_pineapple.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("com/example/magicfruitsgame/images/symbol_strawberry.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("com/example/magicfruitsgame/images/symbol_watermelon.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("com/example/magicfruitsgame/images/symbol_seven.png")))};
        reels[1] = new Image[]{new Image(Objects.requireNonNull(getClass().getResourceAsStream("com/example/magicfruitsgame/images/symbol_cherry.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("com/example/magicfruitsgame/images/symbol_plum.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("com/example/magicfruitsgame/images/symbol_orange.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("com/example/magicfruitsgame/images/symbol_pineapple.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("com/example/magicfruitsgame/images/symbol_strawberry.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("com/example/magicfruitsgame/images/symbol_watermelon.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("com/example/magicfruitsgame/images/symbol_seven.png")))};
        reels[2] = new Image[]{new Image(Objects.requireNonNull(getClass().getResourceAsStream("com/example/magicfruitsgame/images/symbol_cherry.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("com/example/magicfruitsgame/images/symbol_plum.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("com/example/magicfruitsgame/images/symbol_orange.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("com/example/magicfruitsgame/images/symbol_pineapple.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("com/example/magicfruitsgame/images/symbol_strawberry.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("com/example/magicfruitsgame/images/symbol_watermelon.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("com/example/magicfruitsgame/images/symbol_seven.png")))};

        // Inicjalizacja kontenerów na symbole
        symbols = new ImageView[REEL_COUNT][];
        for (int i = 0; i < REEL_COUNT; i++) {
            symbols[i] = new ImageView[3];
            for (int j = 0; j < 3; j++) {
                symbols[i][j] = new ImageView(reels[i][j]);
                symbols[i][j].setTranslateY(-SYMBOL_HEIGHT);
            }
        }
    }

    private void startSpinAnimation() {
        // Losowanie symboli na walcach
        int[][] spinSymbols = new int[REEL_COUNT][];
        for (int i = 0; i < REEL_COUNT; i++) {
            spinSymbols[i] = reelService.spin();
        }

        // Rozpoczęcie animacji przesunięcia symboli na walcach
        for (int i = 0; i < REEL_COUNT; i++) {
            TranslateTransition transition = new TranslateTransition(Duration.millis(ANIMATION_DURATION), symbols[i][0]);
            transition.setToY(0);
            transition.play();
            for (int j = 1; j < 3; j++) {
                TranslateTransition transition1 = new TranslateTransition(Duration.millis(ANIMATION_DURATION), symbols[i][j]);
                transition1.setToY(SYMBOL_HEIGHT * j);
                transition1.play();
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

}
