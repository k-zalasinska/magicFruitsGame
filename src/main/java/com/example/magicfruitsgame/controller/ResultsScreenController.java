//package com.example.magicfruitsgame.controller;
//
//import com.example.magicfruitsgame.service.GameService;
//import javafx.fxml.FXML;
//import javafx.scene.control.Label;
//
//public class ResultsScreenController {
//    @FXML
//    private Label resultLabel;
//
//    private final GameService gameService;
//
//    public ResultsScreenController(GameService gameService) {
//        this.gameService = gameService;
//    }
//
//    @FXML
//    private void initialize() {
//        displayResult();
//    }
//
//    private void displayResult() {
//        String result = "Your result: " + gameService.getBalance(); // Uzyskanie wyniku gry z serwisu gry
//        resultLabel.setText(result);
//    }
//}
