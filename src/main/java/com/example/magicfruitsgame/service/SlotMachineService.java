package com.example.magicfruitsgame.service;

import com.example.magicfruitsgame.controller.SlotMachineController;
import com.example.magicfruitsgame.model.Symbol;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
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

    public int[] play() {
        int[][] reelsSymbols = new int[3][];

        // Obrót każdego z trzech bębnów
        for (int i = 0; i < 3; i++) {
            reelsSymbols[i] = reelService.spin(i);
        }

        // Zwracamy symbole z drugiego bębna, ponieważ to właśnie na nim wyświetlany jest wynik
        return reelsSymbols[1];
    }

    public void animateSpinning(int[][] spinSymbols) {
        int frameDuration = 3; // czas trwania jednego KeyFrame'a
        int cycles = 9 / frameDuration; // liczba cykli animacji
        Timeline timeline = new Timeline(); // tworzymy nową animację

        for (int i = 0; i < cycles; i++) {
            int cycleIndex = i; // index cyklu, używany w lambdzie

            // Tworzymy KeyFrame dla każdego cyklu
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(frameDuration * i), e -> {
                // Wywołujemy metodę aktualizacji bębnów dla danego cyklu
                updateReels(spinSymbols, cycleIndex);
            });

            // Dodajemy KeyFrame do animacji
            timeline.getKeyFrames().add(keyFrame);
        }

        // Odpalamy animację
        timeline.play();
    }

    private void updateReels(int[][] spinSymbols, int cycleIndex) {
        int numberOfSymbols = spinSymbols.length;
        int symbolsPerReel = spinSymbols[0].length;

        for (int i = 0; i < numberOfSymbols; i++) {
            for (int j = 0; j < symbolsPerReel; j++) {
                int symbolIndex = spinSymbols[i][j];
                // Możemy użyć cyklu, aby określić, który zestaw symboli należy wyświetlić
                int adjustedIndex = (symbolIndex + cycleIndex) % symbolService.getSymbols().size();
                Symbol symbol = symbolService.getSymbols().get(adjustedIndex);
                ImageView imageView = reelImageViews[i][j];
                imageView.setImage(symbol.image());
            }
        }
    }

    public void calculateWinAmount(int[][] spinSymbols) {
        // Sprawdź poziome linie
        for (int i = 0; i < 3; i++) {
            int[] symbols = new int[3];
            System.arraycopy(spinSymbols[i], 0, symbols, 0, 3);
            checkWin(symbols);
        }

        // Sprawdź dwie przekątne linie
        int[] diagonalSymbols1 = new int[3];
        int[] diagonalSymbols2 = new int[3];
        for (int i = 0; i < 3; i++) {
            diagonalSymbols1[i] = spinSymbols[i][i];
            diagonalSymbols2[i] = spinSymbols[i][2 - i];
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
    public int[][] spinReels() {
        int[][] reelsSymbols = new int[3][];
        for (int i = 0; i < 3; i++) {
            reelsSymbols[i] = reelService.spin(i);
        }
        return reelsSymbols;
    }

    // Metoda uruchamiająca animację obracania się bębnów
    public void startSpinningAnimation(int[][] spinSymbols) {
        animateSpinning(spinSymbols);
    }

    public Image getSymbolImage(int symbolIndex) {
        return symbolService.getSymbols().get(symbolIndex).image();
    }

    // Metoda rozpoczynająca nową grę
    public void startGame() {
        gameService.startGame();
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

    public ImageView[][] getReelImageViews() {
        return reelImageViews;
    }

    public void setSlotMachineController(SlotMachineController slotMachineController) {
        // Możesz dodać logikę do obsługi kontrolera slotu tutaj
    }
}
