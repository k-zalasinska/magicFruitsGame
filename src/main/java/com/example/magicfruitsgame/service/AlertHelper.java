package com.example.magicfruitsgame.service;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public final class AlertHelper {
    public static void showInfoAlert(String title, String message) {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle(title);
        infoAlert.setHeaderText(null);
        infoAlert.setContentText(message);
        infoAlert.showAndWait();
    }

    public static void showErrorAlert(String title, String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    public static void showWinAlert(int lastWin) {
        Platform.runLater(() -> {
            Alert winAlert = new Alert(Alert.AlertType.INFORMATION);
            winAlert.setTitle("Congratulations!");
            winAlert.setHeaderText(null);
            winAlert.setContentText("You won: " + lastWin);
            winAlert.showAndWait();
        });

    }
}
