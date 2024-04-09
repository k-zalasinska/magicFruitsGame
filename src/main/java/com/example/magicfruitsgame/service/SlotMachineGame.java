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
    public ReelService reelService;

    private static final int SYMBOL_HEIGHT = 100;
    private static final int REEL_COUNT = 3;
    private static final int ANIMATION_DURATION = 3000;

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

        // inicjaliz kontenerów na symbole
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
        // losowanie symboli na walcach
        int[][] spinSymbols = new int[REEL_COUNT][];
        for (int i = 0; i < REEL_COUNT; i++) {
            spinSymbols[i] = reelService.spin();
        }

        //rozpocz. animacji przesuw. symb. na walcach
        for (int i = 0; i < REEL_COUNT; i++) {
            for (int j = 0;j < 3;j++) {
                TranslateTransition transition = getTranslateTransition(spinSymbols, i, j);
                transition.play();
            }
        }
    }

    private TranslateTransition getTranslateTransition(int[][] spinSymbols, int i, int j) {
        int symbolIndex = spinSymbols[i][j];
        int finalJ = j;

        //ustawienie anim. przesun. symb na odpowiednią pozycję
        TranslateTransition transition = new TranslateTransition(Duration.millis(ANIMATION_DURATION), symbols[i][j]);
        transition.setToY(SYMBOL_HEIGHT * finalJ);
        int finalI = i;
        transition.setOnFinished(event -> {
            //aktualizacja symbolu na walcu
            symbols[finalI][finalJ].setImage(reels[finalI][symbolIndex]);
        });
        return transition;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
