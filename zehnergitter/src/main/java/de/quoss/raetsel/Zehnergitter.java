package de.quoss.raetsel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Zehnergitter {

    private int anzahlPermutationen = 0;
    private int[][] allePermutationen = new int[3628800][10];

    private int[] lastPermutation = new int[10];


    private void storePermutation(int[] input) throws ZehnergitterException {
        boolean lastPermutationMatch = true;
        for (int i = 0; i < 10; i++) {
            if (input[i] != lastPermutation[i]) {
                lastPermutationMatch = false;
                break;
            }
        }
        if (lastPermutationMatch) {
            throw new ZehnergitterException(
                    String.format("Storing the same permutation as before, count %s, permutation %s.",
                            anzahlPermutationen, Arrays.toString(input)));
        } else {
            System.arraycopy(input, 0, lastPermutation, 0, input.length);
        }
        // Hier war der Fehler, nicht über '=' zuweisen, sondern Array mit Funktion kopieren!
        System.arraycopy(input, 0, allePermutationen[anzahlPermutationen++], 0, input.length);
    }

    private void swap(int[] input, int a, int b) {
        int tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }

    void createPermutations(int n, int[] elements) throws ZehnergitterException {
        if (n == 1) {
            storePermutation(elements);
        } else {
            for (int i = 0; i < n - 1; i++) {
                createPermutations(n - 1, elements);
                if (n % 2 == 0) {
                    swap(elements, i, n - 1);
                } else {
                    swap(elements, 0, n - 1);
                }
            }
            createPermutations(n - 1, elements);
        }
    }

    int getAnzahlPermutationen() {
        return anzahlPermutationen;
    }

    int[] getPermutation(int i) throws ZehnergitterException {
        if (i < 0 || i >= allePermutationen.length) {
            throw new ZehnergitterException(String.format("Index out of range [0,%s]: %s",
                    (allePermutationen.length - 1), i));
        }
        return allePermutationen[i];
    }

    List<int[]> filterZeile1() {

        List<int[]> interim = new ArrayList<int[]>();
        List<int[]> result = new ArrayList<int[]>();

        for (int i = 0; i < 3628800; i++) {
            if (allePermutationen[i][1] == 8 && allePermutationen[i][4] == 7 && allePermutationen[i][8] == 0
                    && allePermutationen[i][9] == 1) {
                interim.add(allePermutationen[i]);
            }
        }

        System.out.println("Länge Interim Zeile 1: " + interim.size());

        for (int i = 0; i < interim.size(); i++) {
            if (interim.get(i)[0] != 2 && interim.get(i)[5] != 3 && interim.get(i)[6] != 3) {
                result.add(interim.get(i));
            }
        }

        System.out.println("Länge Result Zeile 1: " + result.size());
        return result;
    }

    List<int[]> filterZeile2() {

        List<int[]> interim = new ArrayList<int[]>();
        List<int[]> result = new ArrayList<int[]>();

        for (int i = 0; i < 3628800; i++) {
            if (allePermutationen[i][0] == 2 && allePermutationen[i][5] == 3 && allePermutationen[i][9] == 7) {
                interim.add(allePermutationen[i]);
            }
        }

        System.out.println("Länge Interim Zeile 2: " + interim.size());

        for (int i = 0; i < interim.size(); i++) {
            if (interim.get(i)[1] != 8 && interim.get(i)[2] != 8 && interim.get(i)[1] != 9 && interim.get(i)[1] != 7
                    && interim.get(i)[2] != 7 && interim.get(i)[2] != 6 && interim.get(i)[3] != 6 && interim.get(i)[4] != 6
                    && interim.get(i)[3] != 7 && interim.get(i)[4] != 7 && interim.get(i)[4] != 5
                    && interim.get(i)[6] != 5 && interim.get(i)[7] != 0 && interim.get(i)[8] != 0 && interim.get(i)[8] != 1) {
                result.add(interim.get(i));
            }
        }

        System.out.println("Länge Result Zeile 2: " + result.size());
        return result;
    }

    List<int[]> filterZeile3() {

        List<int[]> interim = new ArrayList<int[]>();
        List<int[]> result = new ArrayList<int[]>();

        for (int i = 0; i < 3628800; i++) {
            if (allePermutationen[i][0] == 9 && allePermutationen[i][1] == 7 && allePermutationen[i][3] == 6 && allePermutationen[i][5] == 5
                    && allePermutationen[i][9] == 0) {
                interim.add(allePermutationen[i]);
            }
        }

        System.out.println("Länge Interim Zeile 3: " + interim.size());

        for (int i = 0; i < interim.size(); i++) {
            if (interim.get(i)[2] != 0 && interim.get(i)[2] != 3 && interim.get(i)[4] != 3 && interim.get(i)[6] != 3
                    && interim.get(i)[6] != 1 && interim.get(i)[7] != 1 && interim.get(i)[8] != 1 && interim.get(i)[8] != 7
                    && interim.get(i)[8] != 8) {
                result.add(interim.get(i));
            }
        }

        System.out.println("Länge Result Zeile 3: " + result.size());
        return result;
    }

    List<int[]> filterZeile4() {

        List<int[]> interim = new ArrayList<int[]>();
        List<int[]> result = new ArrayList<int[]>();

        for (int i = 0; i < 3628800; i++) {
            if (allePermutationen[i][0] == 4 && allePermutationen[i][1] == 0 && allePermutationen[i][2] == 3 && allePermutationen[i][7] == 1
                    && allePermutationen[i][9] == 8) {
                interim.add(allePermutationen[i]);
            }
        }

        System.out.println("Länge Interim Zeile 4: " + interim.size());

        for (int i = 0; i < interim.size(); i++) {
            if (interim.get(i)[3] != 1 && interim.get(i)[3] != 6 && interim.get(i)[4] != 1 && interim.get(i)[4] != 6
                    && interim.get(i)[4] != 5 && interim.get(i)[5] != 5 && interim.get(i)[6] != 5 && interim.get(i)[5] != 4
                    && interim.get(i)[6] != 4 && interim.get(i)[8] != 0) {
                result.add(interim.get(i));
            }
        }

        System.out.println("Länge Result Zeile 4: " + result.size());
        return result;
    }

    List<int[]> filterZeile5() {

        List<int[]> interim = new ArrayList<int[]>();
        List<int[]> result = new ArrayList<int[]>();

        for (int i = 0; i < 3628800; i++) {
            if (allePermutationen[i][3] == 1 && allePermutationen[i][6] == 4 && allePermutationen[i][9] == 0) {
                interim.add(allePermutationen[i]);
            }
        }

        System.out.println("Länge Interim Zeile 5: " + interim.size());

        for (int i = 0; i < interim.size(); i++) {
            if (interim.get(i)[0] != 4 && interim.get(i)[1] != 4 && interim.get(i)[0] != 0 && interim.get(i)[1] != 0
                    && interim.get(i)[2] != 0 && interim.get(i)[1] != 3 && interim.get(i)[2] != 3 && interim.get(i)[0] != 1
                    && interim.get(i)[1] != 1 && interim.get(i)[2] != 1 && interim.get(i)[5] != 3 && interim.get(i)[7] != 3
                    && interim.get(i)[7] != 1 && interim.get(i)[8] != 1 && interim.get(i)[7] != 8 && interim.get(i)[8] != 8
                    && interim.get(i)[8] != 7) {
                result.add(interim.get(i));
            }
        }

        System.out.println("Länge Result Zeile 5: " + result.size());
        return result;
    }

    List<int[]> filterZeile6() {

        List<int[]> interim = new ArrayList<int[]>();
        List<int[]> result = new ArrayList<int[]>();

        for (int i = 0; i < 3628800; i++) {
            if (allePermutationen[i][1] == 1 && allePermutationen[i][6] == 3 && allePermutationen[i][8] == 8 && allePermutationen[i][9] == 7) {
                interim.add(allePermutationen[i]);
            }
        }

        System.out.println("Länge Interim Zeile 6: " + interim.size());

        for (int i = 0; i < interim.size(); i++) {
            if (interim.get(i)[2] != 1 && interim.get(i)[3] != 1 && interim.get(i)[4] != 1 && interim.get(i)[5] != 4
                    && interim.get(i)[7] != 4) {
                result.add(interim.get(i));
            }
        }

        System.out.println("Länge Result Zeile 6: " + result.size());
        return result;
    }



    public void run() throws ZehnergitterException {

        createPermutations(10, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9});

        List<int[]> zeile_1 = filterZeile1();
        List<int[]> zeile_2 = filterZeile2();
        List<int[]> zeile_3 = filterZeile3();
        List<int[]> zeile_4 = filterZeile4();
        List<int[]> zeile_5 = filterZeile5();
        List<int[]> zeile_6 = filterZeile6();
        int zaehler = 0;

        System.out.println();
        for (int i = 0; i < zeile_1.size(); i++) {
            System.out.print("a(" + i + ")");
            for (int j = 0; j < zeile_2.size(); j++) {
                System.out.print("b(" + j + ")");
                for (int k = 0; k < zeile_3.size(); k++) {
                    for (int l = 0; l < zeile_4.size(); l++) {
                        for (int m = 0; m < zeile_5.size(); m++) {
                            for (int n = 0; n < zeile_6.size(); n++) {

                                if (15 + zeile_1.get(i)[0] + zeile_5.get(m)[0] + zeile_6.get(n)[0] == 29
                                        && 16 + zeile_2.get(j)[1] + zeile_5.get(m)[1] == 30
                                        && 3 + zeile_1.get(i)[2] + zeile_2.get(j)[2] + zeile_3.get(k)[2] + zeile_5.get(m)[2] + zeile_6.get(n)[2] == 31
                                        && 7 + zeile_1.get(i)[3] + zeile_2.get(j)[3] + zeile_4.get(l)[3] + zeile_6.get(n)[3] == 30
                                        && 7 + zeile_2.get(j)[4] + zeile_3.get(k)[4] + zeile_4.get(l)[4] + zeile_5.get(m)[4] + zeile_6.get(n)[4] == 23
                                        && 8 + zeile_1.get(i)[5] + zeile_4.get(l)[5] + zeile_5.get(m)[5] + zeile_6.get(n)[5] == 22
                                        && 7 + zeile_1.get(i)[6] + zeile_2.get(j)[6] + zeile_3.get(k)[6] + zeile_4.get(l)[6] == 19
                                        && 1 + zeile_1.get(i)[7] + zeile_2.get(j)[7] + zeile_3.get(k)[7] + zeile_5.get(m)[7] + zeile_6.get(n)[7] == 34
                                        && 8 + zeile_2.get(j)[8] + zeile_3.get(k)[8] + zeile_4.get(l)[8] + zeile_5.get(n)[8] == 29) {

                                    String Ausgabe;
                                    Ausgabe = "";

                                    System.out.println("Lösung Nummer " + zaehler + ":");

                                    for (int x = 0; x < 10; x++) {
                                        Ausgabe += Integer.toString(zeile_1.get(i)[x]) + " ";
                                    }
                                    System.out.println(Ausgabe);
                                    Ausgabe = "";

                                    for (int x = 0; x < 10; x++) {
                                        Ausgabe += Integer.toString(zeile_2.get(j)[x]) + " ";
                                    }
                                    System.out.println(Ausgabe);
                                    Ausgabe = "";

                                    for (int x = 0; x < 10; x++) {
                                        Ausgabe += Integer.toString(zeile_3.get(k)[x]) + " ";
                                    }
                                    System.out.println(Ausgabe);
                                    Ausgabe = "";

                                    for (int x = 0; x < 10; x++) {
                                        Ausgabe += Integer.toString(zeile_4.get(l)[x]) + " ";
                                    }
                                    System.out.println(Ausgabe);
                                    Ausgabe = "";

                                    for (int x = 0; x < 10; x++) {
                                        Ausgabe += Integer.toString(zeile_5.get(m)[x]) + " ";
                                    }
                                    System.out.println(Ausgabe);
                                    Ausgabe = "";

                                    for (int x = 0; x < 10; x++) {
                                        Ausgabe += Integer.toString(zeile_6.get(n)[x]) + " ";
                                    }
                                    System.out.println(Ausgabe);
                                    zaehler++;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (zaehler == 0)
            System.out.println("Das war wohl nichts!");
        else
            System.out.println("Hurrah! Wir haben eine Lösung!");
    }

    public static void main(String[] args) {
        try {
            new Zehnergitter().run();
        } catch (ZehnergitterException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}
