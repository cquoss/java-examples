package de.quoss.raetsel;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class ZehnergitterTest {
    @Test
    public void createPermutationsTest() throws Exception {
        Zehnergitter zehnergitter = new Zehnergitter();
        int[] array = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        long startMillis = System.currentTimeMillis();
        zehnergitter.createPermutations(array.length, array);
        System.out.println("Laufzeit (Millis): " + (System.currentTimeMillis() - startMillis));
        System.out.println("Anzahl Permutationen: " + zehnergitter.getAnzahlPermutationen());
        for (int i = 0; i < 25; i++) {
            System.out.println(String.format("Permutation [%s]: %s", i,
                    Arrays.toString(zehnergitter.getPermutation(i))));
        }
    }
}
