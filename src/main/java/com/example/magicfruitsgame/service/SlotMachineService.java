package com.example.magicfruitsgame.service;

import com.example.magicfruitsgame.model.Symbol;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SlotMachineService {
    private final SymbolService symbolService = new SymbolService();
    private final ReelService reelService;
    private final GameService gameService;
    private int lastWin;
    private ImageView[][] reelImageViews;


    public SlotMachineService(ReelService reelService, GameService gameService) {
        this.reelService = reelService;
        this.gameService = gameService;


        // Inicjalizacja tablicy reelImageViews
        reelImageViews = new ImageView[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                reelImageViews[i][j] = new ImageView();
            }
        }
    }


    public void setReelImageViews(ImageView[][] reelImageViews) {
        this.reelImageViews = reelImageViews;
    }

    public GameService getGameService() {
        return gameService;
    }

    public int[] spinReels() {
        int[] allSymbols = new int[9]; // Tablica na wszystkie symbole z trzech bębnów

        // Losujemy symbole dla każdego bębna i dodajemy do tablicy allSymbols
        for (int i = 0; i < 3; i++) {
            int[] symbolsForReel = reelService.spin(i);
            System.arraycopy(symbolsForReel, 0, allSymbols, i * 3, 3); // Kopiujemy symbole z bębna do tablicy allSymbols
        }

        return allSymbols;
    }

    public void animateSpinning(int[] spinSymbols) {
        int frameDuration = 3; // czas trwania jednego KeyFrame'a
        int cycles = 1; // liczba cykli animacji
        Timeline timeline = new Timeline(); // tworzymy nową animację

        for (int i = 0; i < cycles; i++) {
            int cycleIndex = i; // index cyklu, używany w lambdzie

            // Tworzymy KeyFrame dla każdego cyklu
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(0), e -> {
                // Przesuwamy symbole na bębnach w dół
                for (int j = spinSymbols.length - 1; j >= 0; j--) {
                    if (j == 0) {
                        // Jeśli to pierwszy wiersz, przesuwamy symbole na dół
                        spinSymbols[j] = reelService.spin(j)[0];
                    } else {
                        // W przeciwnym razie, przesuwamy symbole w dół
                        spinSymbols[j] = spinSymbols[j - 1];
                    }
                }
                // Wywołujemy metodę aktualizacji bębnów dla danego cyklu
                updateReels(spinSymbols, cycleIndex);
            });

            // Dodajemy KeyFrame do animacji
            timeline.getKeyFrames().add(keyFrame);
        }

        // Odpalamy animację
        timeline.play();

        // Dodajmy wywołanie evaluateResult na końcu animacji
        timeline.setOnFinished(event -> evaluateResult(spinSymbols));
    }

    public void evaluateResult(int[] spinSymbols) {
        boolean horizontalWin = checkHorizontalWin(spinSymbols);
        boolean diagonalWin = checkDiagonalWin(spinSymbols);

        if (horizontalWin || diagonalWin) {
            calculateWinAmount(spinSymbols);
            System.out.println("Congratulations! You won " + lastWin + " credits.");
        } else {
            setLastWin(0);
            System.out.println("No win. Try again!");
        }
    }

    private boolean checkHorizontalWin(int[] spinSymbols) {
        for (int i = 0; i < 3; i++) {
            int startIndex = i * 3;
            if (spinSymbols[startIndex] == spinSymbols[startIndex + 1] &&
                    spinSymbols[startIndex + 1] == spinSymbols[startIndex + 2]) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonalWin(int[] spinSymbols) {
        return (spinSymbols[0] == spinSymbols[4] && spinSymbols[4] == spinSymbols[8]) ||
                (spinSymbols[2] == spinSymbols[4] && spinSymbols[4] == spinSymbols[6]);
    }

    private void updateReels(int[] spinSymbols, int cycleIndex) {
        int symbolsPerReel = spinSymbols.length;

        // Przesuwamy symbole w dół
        for (int i = symbolsPerReel - 1; i > 0; i--) {
            spinSymbols[i] = spinSymbols[i - 1];
        }

        // Losujemy nowy symbol dla pierwszego bębna
        int[] symbols = reelService.spin(0); // Tablica symboli dla danego bębna
        spinSymbols[0] = symbols[0]; // Wybieramy pierwszy symbol z tablicy

        // Aktualizujemy obrazy symboli w bębnach
        for (int i = 0; i < symbolsPerReel; i++) {
            int symbolIndex = spinSymbols[i];
            int adjustedIndex = (symbolIndex + cycleIndex) % symbolService.getSymbols().size();
            Symbol symbol = symbolService.getSymbols().get(adjustedIndex);
            ImageView[] imageViews = reelImageViews[i];
            imageViews[0].setImage(symbol.image()); // Przypisanie obrazu do pierwszego ImageView w danym bębnie
        }
    }

    public void calculateWinAmount(int[] spinSymbols) {
        for (int i = 0; i < 3; i++) {
            int startIndex = i * 3;
            int[] symbols = new int[3];
            System.arraycopy(spinSymbols, startIndex, symbols, 0, 3);
            checkWin(symbols);
        }

        int[] diagonalSymbols1 = new int[3];
        int[] diagonalSymbols2 = new int[3];
        for (int i = 0; i < 3; i++) {
            diagonalSymbols1[i] = spinSymbols[i * 3 + i];
            diagonalSymbols2[i] = spinSymbols[i * 3 + 2 - i];
        }
        checkWin(diagonalSymbols1);
        checkWin(diagonalSymbols2);
    }


    private void checkWin(int[] symbols) {
        if (symbols[0] == symbols[1] && symbols[1] == symbols[2]) {
            int multiplier = symbolService.getSymbol(symbols[0]).winMultiplier();
            int winAmount = 10 * multiplier;
            setLastWin(winAmount);
        }
    }

    // Metoda do obracania bębnami
//    public int[][] spinReels() {
//        int[][] reelsSymbols = new int[3][];
//        for (int i = 0; i < 3; i++) {
//            reelsSymbols[i] = reelService.spin(i);
//        }
//        return reelsSymbols;
//    }


    // Metoda uruchamiająca animację obracania się bębnów
    public void startSpinningAnimation(int[] spinSymbols) {
        animateSpinning(spinSymbols);
    }

    // Metoda doładowująca stan konta
    public void topUpBalance(int amount) {
        gameService.topUpBalance(amount);
    }

    // Metoda zwracająca stawkę gry
    public int getStake() {
        return gameService.getGame().getStake();
    }

    // Metoda zwracająca aktualny stan konta
    public int getBalance() {
        return gameService.getBalance();
    }

    public int getLastWin() {
        return lastWin;
    }

    public void setLastWin(int lastWin) {
        this.lastWin = lastWin;
    }

}
