package com.example.magicfruitsgame;

import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Label balanceLabel = new Label(); // Tworzenie etykiety salda

        SlotMachineUI slotMachineUI = new SlotMachineUI(balanceLabel);
        slotMachineUI.start(primaryStage); // Uruchomienie interfejsu użytkownika
    }

    public static void main(String[] args) {
        launch(args);
    }
}
