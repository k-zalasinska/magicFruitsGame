package com.example.magicfruitsgame;

import com.example.magicfruitsgame.controller.AnimationController;
import com.example.magicfruitsgame.controller.SlotMachineController;
import com.example.magicfruitsgame.model.Game;
import com.example.magicfruitsgame.service.GameService;
import com.example.magicfruitsgame.service.ReelService;
import com.example.magicfruitsgame.service.SlotMachineService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            int[][] reelsDefinition = new int[][]{
                    /* reel 1 */  new int[]{0, 4, 0, 0, 0, 2, 2, 3, 1, 4, 0, 6, 0, 1, 0, 0, 5, 1, 1, 3},
                    /* reel 2 */  new int[]{2, 5, 1, 2, 0, 1, 3, 5, 2, 6, 1, 0, 0, 0, 1, 3, 4, 4, 0, 4},
                    /* reel 3 */  new int[]{5, 2, 1, 0, 0, 3, 4, 0, 2, 6, 0, 2, 0, 0, 0, 1, 1, 3, 0, 4}
            };

            ReelService reelService = new ReelService(reelsDefinition);
            GameService gameService = new GameService(new Game());
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/slot_machine.fxml"));
            Game game = new Game();
            loader1.setControllerFactory(controllerClass -> new SlotMachineController(new SlotMachineService(reelService, gameService)));
            URL symbolsUrl = getClass().getResource("/symbols.fxml");

            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/animation.fxml"));
            loader2.setControllerFactory(controllerClass -> new AnimationController(new SlotMachineService(reelService, gameService)));


            Parent root = loader1.load();

            // Pobierz scenę z załadowanego korzenia
            Scene scene = new Scene(root, 600, 400);

            // Dodaj arkusz stylów do sceny
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());

            primaryStage.setTitle("Slot Machine Game");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
