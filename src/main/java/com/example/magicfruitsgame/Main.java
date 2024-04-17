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

        loader.setControllerFactory(controllerClass -> {
            if (SlotMachineController.class.isAssignableFrom(controllerClass)) {
                return new SlotMachineController(new GameService(new SymbolService()));
            } else {
                try {
                    return controllerClass.getDeclaredConstructor().newInstance();
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


    public static void main(String[] args) {
        launch(args);
    }
}
