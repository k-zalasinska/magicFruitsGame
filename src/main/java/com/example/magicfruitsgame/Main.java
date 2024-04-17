package com.example.magicfruitsgame;

import com.example.magicfruitsgame.controller.SlotMachineController;
import com.example.magicfruitsgame.service.GameService;
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

        GameService gameService = createGameService();
        loader.setControllerFactory(x -> {
            if (x == SlotMachineController.class) {
                return new SlotMachineController(gameService);
            } else {
                try {
                    return x.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Parent root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Slot Machine Game");
        primaryStage.show();
    }

    private GameService createGameService() {
        SymbolService symbolService = new SymbolService();
        return new GameService(symbolService);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
