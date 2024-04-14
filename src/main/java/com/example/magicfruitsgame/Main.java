package com.example.magicfruitsgame;

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

public class Main extends Application {

    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/slot_machine.fxml"));
            Parent root = loader.load();
            SlotMachineController controller = loader.getController();

            int[][] reelsDefinition = {
                    /* reel 1 */  {0, 4, 0, 0, 0, 2, 2, 3, 1, 4, 0, 6, 0, 1, 0, 0, 5, 1, 1, 3},
                    /* reel 2 */  {2, 5, 1, 2, 0, 1, 3, 5, 2, 6, 1, 0, 0, 0, 1, 3, 4, 4, 0, 4},
                    /* reel 3 */  {5, 2, 1, 0, 0, 3, 4, 0, 2, 6, 0, 2, 0, 0, 0, 1, 1, 3, 0, 4}
            };

            ReelService reelService = new ReelService(reelsDefinition);
            GameService gameService = new GameService(new Game());

            System.out.println("reelService: " + reelService);
            System.out.println("gameService: " + gameService);

            SlotMachineService slotMachineService = new SlotMachineService(reelService, gameService);

            // Inicjalizacja ImageView dla bębnów
            controller.initializeReelImageViews();

            // Ustawienie ImageView w serwisie
            slotMachineService.setReelImageViews(controller.getReelImageViews());

            // Ustawienie kontrolera w serwisie
            controller.setSlotMachineService(slotMachineService);

            Scene scene = new Scene(root, 800, 600);
            primaryStage.setTitle("Slot Machine Game");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
