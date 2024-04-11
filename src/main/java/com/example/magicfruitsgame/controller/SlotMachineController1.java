//package com.example.magicfruitsgame.controller;
//
//import com.example.magicfruitsgame.model.Game;
//import com.example.magicfruitsgame.model.Symbol;
//import com.example.magicfruitsgame.service.ReelService;
//import com.example.magicfruitsgame.service.SymbolService;
//import javafx.animation.KeyFrame;
//import javafx.animation.Timeline;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextInputDialog;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.GridPane;
//import javafx.util.Duration;
//
//import java.util.Optional;
//
//public class SlotMachineController1 {
//
//    @FXML
//    private GridPane reelsGridPane;
//
//    @FXML
//    private ImageView reel1Symbol1;
//
//    @FXML
//    private ImageView reel1Symbol2;
//
//    @FXML
//    private ImageView reel1Symbol3;
//
//    // Define other ImageView variables for the rest of the reels and symbols
//
//    @FXML
//    private Button startButton;
//
//    @FXML
//    private Button payInButton;
//
//    @FXML
//    private Button autoSpinButton;
//
//    @FXML
//    private Label balanceLabel;
//
//    private Game game;
//    private final ReelService reelService = new ReelService();
//    private final Symbol[] symbols = SymbolService.createSymbols();
////    private final SlotMachineService slotMachineService = new SlotMachineService(player, game, symbols, reelsDefinition);
//    private boolean autoSpinEnabled = false;
//
//    public SlotMachineController1() {
//        this.game = new Game();
//        // Initialize other necessary components here
//    }
//
//    @FXML
//    private void initialize() {
//        // Initialize the UI components and setup event handlers
//        balanceLabel.setText("Balance: " + game.getBalance());
//    }
//
//    @FXML
//    private void handleStartButton() {
//        // Losowanie symboli dla każdego walca
//        int[][] reelsSymbols = new int[][]{reelService.spin()};
//
//        // Wyświetlanie symboli na walcach
//        for (int i = 0; i < reelsSymbols.length; i++) {
//            for (int j = 0; j < reelsSymbols[i].length; j++) {
//                int symbolId = reelsSymbols[i][j];
//                ImageView imageView = new ImageView(symbols[symbolId].getImage());
//                reelsGridPane.add(imageView, i, j);
//            }
//        }
//    }
//
//    @FXML
//    private void handlePayInButton(ActionEvent event) {
//        // Handle logic for when the pay in button is clicked
//        TextInputDialog dialog = new TextInputDialog();
//        dialog.setTitle("Pay In");
//        dialog.setHeaderText("Enter the amount to deposit:");
//        dialog.setContentText("Amount:");
//
//        Optional<String> result = dialog.showAndWait();
//        if (result.isPresent()) {
//            try {
//                int amount = Integer.parseInt(result.get());
//                if (amount > 0) {
//                    game.setBalance(game.getBalance() + amount);
//                    balanceLabel.setText("Balance: " + game.getBalance());
//                } else {
//                    showAlert("Invalid amount", "Please enter a positive amount.");
//                }
//            } catch (NumberFormatException ex) {
//                showAlert("Invalid input", "Please enter a valid number.");
//            }
//        }
//    }
//
//    @FXML
//    private void handleAutoSpinButton(ActionEvent event) {
//        // Handle logic for when the auto spin button is clicked
//        autoSpinEnabled = !autoSpinEnabled;
//        if (autoSpinEnabled) {
//            startAutoSpin();
//        }
//    }
//
//    private void startAutoSpin() {
//        Timeline autoSpinTimeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
//            if (autoSpinEnabled) {
//                slotMachineService.play(new ImageView[][]{ /* Pass the ImageView matrix here */ });
//            }
//        }));
//        autoSpinTimeline.setCycleCount(Timeline.INDEFINITE);
//        autoSpinTimeline.play();
//    }
//
//    private void showAlert(String title, String message) {
//        Alert alert = new Alert(Alert.AlertType.WARNING);
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
//}
