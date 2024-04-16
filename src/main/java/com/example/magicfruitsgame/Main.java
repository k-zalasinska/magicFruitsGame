package com.example.magicfruitsgame;

import com.example.magicfruitsgame.controller.SlotMachineController;
import com.example.magicfruitsgame.model.Game;
import com.example.magicfruitsgame.service.GameService;
import com.example.magicfruitsgame.service.ReelService;
import com.example.magicfruitsgame.service.SymbolService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

//public class Main extends Application {
//    @Override
//    public void start(Stage primaryStage) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/slot_machine.fxml"));
//        Parent root = loader.load();
//
//        Scene scene = new Scene(root, 1000, 60);
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("Slot Machine Game");
//        primaryStage.show();
//
//        int[][] reelsDefinition = {
//                /* reel 1 */  {0, 4, 0, 0, 0, 2, 2, 3, 1, 4, 0, 6, 0, 1, 0, 0, 5, 1, 1, 3},
//                /* reel 2 */  {2, 5, 1, 2, 0, 1, 3, 5, 2, 6, 1, 0, 0, 0, 1, 3, 4, 4, 0, 4},
//                /* reel 3 */  {5, 2, 1, 0, 0, 3, 4, 0, 2, 6, 0, 2, 0, 0, 0, 1, 1, 3, 0, 4}
//        };
//
//        SymbolService symbolService = new SymbolService();
//        ReelService reelService = new ReelService(symbolService);
//        Game game = new Game();
//        GameService gameService = new GameService(reelService, symbolService, game);
//
//        loader.setControllerFactory(ignored -> new SlotMachineController(gameService));
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/slot_machine.fxml"));

        // Tworzenie fabryki kontrolerów
        GameService gameService = createGameService();
        loader.setControllerFactory(controllerClass -> {
            if (controllerClass == SlotMachineController.class) {
                return new SlotMachineController(gameService);
            } else {
                try {
                    return controllerClass.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Parent root = loader.load();
// Ustawienie tła w StackPane
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(root);
        stackPane.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        // Tworzenie sceny z StackPane
        Scene scene = new Scene(stackPane); // Ustaw odpowiednie rozmiary
        primaryStage.setScene(scene);
        primaryStage.setTitle("Slot Machine Game");
        primaryStage.show();

//        Scene scene = new Scene(root, 1000, 60);
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("Slot Machine Game");
//        primaryStage.show();
    }

    private GameService createGameService() {
        ReelService reelService = new ReelService(new SymbolService());
        return new GameService(reelService, new SymbolService(), new Game());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
