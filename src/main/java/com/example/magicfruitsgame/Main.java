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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

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


//  główny kontener z pliku FXML
        Parent root = loader.load();

        Image backgroundImage = new Image(getClass().getResourceAsStream("/views/background.jpg"));
        ImageView background = new ImageView(backgroundImage);

// Ustawienie skalowania tła do rozmiaru sceny
        background.setPreserveRatio(true); // zachowanie proporcji obrazu
        // Ustawienie skali obrazu w procentach
        double scaleX = 0.65; // 50% szerokości okna
        double scaleY = 0.83; // 50% wysokości okna

// Ustawienie szerokości obrazu na 50% szerokości okna
        background.fitWidthProperty().bind(primaryStage.widthProperty().multiply(scaleX));

// Ustawienie wysokości obrazu na 50% wysokości okna
        background.fitHeightProperty().bind(primaryStage.heightProperty().multiply(scaleY));
        background.setSmooth(true); // włączenie wygładzania obrazu
        background.setCache(true); // włączenie buforowania obrazu

//  kontener na tło
        StackPane backgroundPane = new StackPane(background);

// Dodaje główną zawartość do kontenera
        backgroundPane.getChildren().add(root);

// Ustawia styl CSS dla kontenera, aby użyć flexboxa
        backgroundPane.setStyle("-fx-display: flex; -fx-flex-direction: column; -fx-align-items: center;");

// tworzy scenę
        Scene scene = new Scene(backgroundPane);

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
