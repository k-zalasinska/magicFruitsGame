package com.example.magicfruitsgame.service;

import javafx.application.Platform;
import javafx.scene.control.Alert;

/**
 * Helper class for displaying different types of alerts in the application.
 */
public final class AlertHelper {

    /**
     * Displays an information alert with the specified title and message.
     *
     * @param title   the title of the alert
     * @param message the message to be displayed in the alert
     */
    public static void showInfoAlert(String title, String message) {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle(title);
        infoAlert.setHeaderText(null);
        infoAlert.setContentText(message);
        infoAlert.showAndWait();
    }

    /**
     * Displays an error alert with the specified title and message.
     *
     * @param title   the title of the alert
     * @param message the message to be displayed in the alert
     */
    public static void showErrorAlert(String title, String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    /**
     * Displays an information alert with the message indicating the winning amount.
     * This method is designed to run on the JavaFX Application Thread.
     *
     * @param lastWin the amount won
     */
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
