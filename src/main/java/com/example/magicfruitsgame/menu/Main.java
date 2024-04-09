package com.example.magicfruitsgame.menu;

import com.example.magicfruitsgame.controller.MainMenuController;
import com.example.magicfruitsgame.model.Player;
import com.example.magicfruitsgame.service.GameService;
import com.example.magicfruitsgame.service.PlayerService;
import com.example.magicfruitsgame.service.ReelService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Main extends Application {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    @Override
    public void start(Stage primaryStage) {
        try {
            // Inicjalizacja serwisów
            PlayerService playerService = new PlayerService(new Player());
            List<ReelService> reels = initializeReels(); // Inicjalizacja bębnów
            GameService gameService = new GameService(reels, playerService);
            // Załadowanie głównego menu
            FXMLLoader loader = new FXMLLoader(getClass().getResource("com/example/magicfruitsgame/ui/menu.fxml"));
            loader.setControllerFactory(controllerClass -> new MainMenuController(playerService, gameService));
            Parent root = loader.load();

            MainMenuController mainMenuController = loader.getController();
            mainMenuController.setGameService(gameService);

            // Ustawienie sceny
            Scene scene = new Scene(root, 600, 400);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Magic Fruits");
            primaryStage.show();
        } catch (IOException e) {
            logger.severe("Error loading FXML file: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error loading FXML file.");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while loading the FXML file.\nPlease check the file path and try again.");
            alert.showAndWait();
        }
    }

    // Metoda inicjalizuj. bębny
    private static List<ReelService> initializeReels() {
        int[][] reelsDefinition = {
                {0, 1, 2, 3, 4, 5, 6},
                {3, 4, 5, 6, 0, 1, 2},
                {6, 5, 4, 3, 2, 1, 0}
        };

        List<ReelService> reels = new ArrayList<>();
        for (int i = 0; i < reelsDefinition.length; i++) {
            reels.add(new ReelService(i, reelsDefinition));
        }

        return reels;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
