package com.example.magicfruitsgame;

import com.example.magicfruitsgame.controller.SlotMachineController;
import com.example.magicfruitsgame.service.GameService;
import com.example.magicfruitsgame.service.SlotMachineService;
import com.example.magicfruitsgame.service.SymbolService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main class responsible for starting the Slot Machine Game application.
 * It extends the JavaFX Application class, which is the entry point for JavaFX applications.
 * The Application class provides the basic framework for managing the JavaFX application lifecycle.
 * It initializes the primary stage, loads the FXML file for the user interface, sets up the scene, and displays the stage.
 * Additionally, it configures the controller factory to inject dependencies into the controller classes.
 */
public class Main extends Application {
    /**
     * The entry point for the application.
     * It launches the JavaFX application by calling the start method.
     *
     * @param args The command-line arguments passed to the application (unused).
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the JavaFX application by initializing the primary stage, loading the FXML file, and displaying the stage.
     *
     * @param primaryStage The primary stage of the JavaFX application.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        // Load the FXML file for the user interface
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/slot_machine.fxml"));

        // Configure the controller factory to inject dependencies into the controller classes
        loader.setControllerFactory(controllerClass -> {
            if (SlotMachineController.class.isAssignableFrom(controllerClass)) {
                return new SlotMachineController(new GameService(new SymbolService()),
                        new SlotMachineService(new GameService(new SymbolService),new GridPane()));
            } else {
                try {
                    return controllerClass.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Load the root element of the FXML file
        Parent root = loader.load();

        // Set up the scene and display the stage
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Slot Machine Game");
        primaryStage.show();
    }
}
