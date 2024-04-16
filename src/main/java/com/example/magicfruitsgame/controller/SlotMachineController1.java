//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        ObservableList<Node> children = reelsImageViews.getChildren();
//
//        reelImageViews = new ImageView[3][3]; // Inicjalizacja pola reelImageViews
//
//        int index = 0;
//
//        for (Node child : children) {
//            if (child instanceof ImageView imageView) {
//                int row = index / 3;
//                int col = index % 3;
//                reelImageViews[row][col] = imageView;
//                index++;
//            }
//        }
////        reelImageViews[row][col] = (ImageView) child;
//        // Ustawienie reelImageViews w SlotMachineService
//        slotMachineService.setReelImageViews(reelImageViews);
//
//        balanceLabel.setText("100");
//        lastWinLabel.setText("0");
//        stakeLabel.setText("10");
//
//        // Dodanie obsługi zdarzeń dla przycisku "Start"
////        startButton.setOnAction(event -> handleStartButton());
//
//        depositAmountField.setVisible(false);
//        payInButton.setOnAction(event -> handlePayInButton());
//
//        balanceLabel.setTranslateX(550);
//        balanceLabel.setTranslateY(815);
//
//        lastWinLabel.setTranslateX(762);
//        lastWinLabel.setTranslateY(770);
//
//        stakeLabel.setTranslateX(939);
//        stakeLabel.setTranslateY(722);
//
//        updateUI();
//    }
//

//
//    public ImageView[][] getReelImageViews() {
//        return reelImageViews;
//    }
//
//    @FXML
//    private Pane container;
//
//    private void drawWinningLine(int startX, int startY, int endX, int endY) {
//        double symbolWidth = 100.0;
//        double symbolHeight = 100.0;
//
//        Line line = new Line();
//        line.setStartX(startX * symbolWidth + symbolWidth / 2);
//        line.setStartY(startY * symbolHeight + symbolHeight / 2);
//        line.setEndX(endX * symbolWidth + symbolWidth / 2);
//        line.setEndY(endY * symbolHeight + symbolHeight / 2);
//        line.setStrokeWidth(3);
//        line.setStroke(Color.RED);
//        // Dodaj przekreślenie do kontenera
//        container.getChildren().add(line);line
//    }
//
////    @FXML
////    private void evaluateResult(int[] spinSymbols) {
////        slotMachineService.evaluateResult(spinSymbols);
////        updateUI();
////
////    }
//}
