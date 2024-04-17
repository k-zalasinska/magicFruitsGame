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
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/slot_machine.fxml"));
        Parent root = loader.load();

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

        Scene scene = new Scene(root);

        // Ustawia scenę w primaryStage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Slot Machine Game");
        primaryStage.show();
    }

    private GameService createGameService() {
        SymbolService symbolService = new SymbolService();
        ReelService reelService = new ReelService(symbolService);
        return new GameService(reelService, new Game());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
