//package com.example.magicfruitsgame.tests;
//
//import com.example.magicfruitsgame.service.Reel;
//import org.junit.Test;
//
//import java.util.Arrays;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//public class ReelTest {
//    @Test
//    public void testSpin() {
//        // Przykładowa definicja bębnów
//        int[][] reelsDefinition = new int[][]{
//                /* reel 1 */  new int[]{0, 4, 0, 0, 0, 2, 2, 3, 1, 4, 0, 6, 0, 1, 0, 0, 5, 1, 1, 3},
//                /* reel 2 */  new int[]{2, 5, 1, 2, 0, 1, 3, 5, 2, 6, 1, 0, 0, 0, 1, 3, 4, 4, 0, 4},
//                /* reel 3 */  new int[]{5, 2, 1, 0, 0, 3, 4, 0, 2, 6, 0, 2, 0, 0, 0, 1, 1, 3, 0, 4}
//        };
//
//        // Tworzenie instancji klasy Reel
//        Reel reel = new Reel(0, reelsDefinition);
//
//        // Wywołanie metody spin()
//        int[] symbols = reel.spin();
//
//        // Sprawdzenie, czy metoda spin() zwraca tablicę o oczekiwanym rozmiarze
//        assertEquals(3, symbols.length);
//
//        // Sprawdzenie, czy zwrócone symbole są zgodne z oczekiwaniami
//        int[] expectedSymbols = reelsDefinition[0]; // Oczekujemy symboli z pierwszego bębna
//        assertTrue(Arrays.stream(expectedSymbols).anyMatch(symbol -> symbol == symbols[0]));
//        assertTrue(Arrays.stream(expectedSymbols).anyMatch(symbol -> symbol == symbols[1]));
//        assertTrue(Arrays.stream(expectedSymbols).anyMatch(symbol -> symbol == symbols[2]));
//    }
//
//}

/**
 * test jednostkowy, który sprawdza poprawność działania tej metody.
 * <p>
 * Test ten sprawdza kilka aspektów metody spin():
 * <p>
 * 1. Sprawdza, czy zwracana tablica ma oczekiwany rozmiar (3 symbole).
 * 2. Sprawdza, czy zwrócone symbole są zgodne z oczekiwanymi symbolami zdefiniowanymi w tablicy reelsDefinition.
 * Jeśli test przechodzi pomyślnie, oznacza to, że metoda spin() zachowuje się zgodnie z oczekiwaniami.
 * Jednak warto również przeprowadzić więcej testów, aby pokryć większą liczbę przypadków,
 * na przykład testować działanie dla różnych wartości reelIndex i różnych definicji bębnów.
 */

