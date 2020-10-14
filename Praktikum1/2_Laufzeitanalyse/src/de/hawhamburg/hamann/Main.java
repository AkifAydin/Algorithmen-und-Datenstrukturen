package de.hawhamburg.hamann;

import java.io.Console;
import java.util.Arrays;

public class Main {

    private static final int NUM_RUNS      = 150;
    private static final int STRING_LENGTH = 100;

    public static void main(String[] args) {
        runBuildString();
    }

    private static void runBuildString() {
        String s = "";

        long startTime = System.nanoTime();

        for (int run = 0; run < NUM_RUNS; ++run) {
            for (int index = 0; index < STRING_LENGTH; ++index) {
                // Aus dem "lesbaren" Bereich
                s += Character.toChars((index % 26) + 65);
            }
        }

        long duration = (long)((double)(System.nanoTime() - startTime) / NUM_RUNS) / 1000000;
        System.out.printf("String creation took %dms in average", duration);
    }
}
