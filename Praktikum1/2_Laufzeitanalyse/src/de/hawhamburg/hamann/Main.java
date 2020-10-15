package de.hawhamburg.hamann;

import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.IntConsumer;

public class Main {

    // Anzahl der Wiederholungen für ein N, um einen Durchschnittswert zu berechnen
    private static final int NUM_RUNS_PER_N =      10;
    // Bei welchem N wollen wir beginnen?
    private static final int N_START        =       0;
    // Um diese Anzahl wird für den nächsten Durchlauf N erhöht
    private static final int N_INCREMENT    =    1000;
    // Dies ist die obere Grenze der Ausführung
    private static final int N_END          =  300000;

    public static void main(String[] args) {
        IntConsumer stringConcat =
        (iCurrentN) -> {
            String s = "";

            for (int index = 0; index < iCurrentN; ++index) {
                s += 'a';
            }};

        runBenchmark(stringConcat);
    }

    /**
     * Grundgerüst des Becnhmarks
     * Führt die Funktion <code>toBenchmark</code> beginnend mit
     * dem in <code>N_START</code> festgelegten Wert aus.
     * Die Ausführung wird <code>NUM_RUNS_PER_N</code> mal wiederholt, um
     * den Mittelwert der Laufzeiten zu ermitteln.
     * Die durchschnittliche Laufzeit wird dann mit dem Wert für <code>N</code>
     * auf der Konsole ausgegeben.
     * Dieser Vorgang wird mit dem nächsten Inkrement (<code>N_INCREMENT</code>)
     * so lange wiederholt bis die obere Grenze <code>N_END</code> erreicht wurde.
     * Somit kann die Laufzeit eines Algorithmus für die Eingaben von
     * <code>N_START</code> bis <code>N_END</code> in den Schritten <code>N_INCREMENT</code>
     * einfach ausgegeben werden.
     * Die Ausgabe lässt sich z. B. in Excel leicht per copy & paste verarbeiten.
     * @param toBenchmark Die auszuführende Funktion
     */
    private static void runBenchmark(IntConsumer toBenchmark) {

        for (int iCurrentN = N_START; iCurrentN <= N_END; iCurrentN += N_INCREMENT) {
            long duration = 0;

            for (int run = 0; run < NUM_RUNS_PER_N; ++run) {
                // Vor der Ausführung aufräumen (Chancengleichheit)
                Runtime.getRuntime().gc();

                // Nur den eigentlichen Algorithmus messen
                long startTime = System.nanoTime();

                toBenchmark.accept(iCurrentN);

                duration += (System.nanoTime() - startTime);
            }

            long averageDuration = (long) ((double)duration  / NUM_RUNS_PER_N) / 1000000;

            // Auf die Konsole damit
            System.out.printf("%d %d%n", iCurrentN, averageDuration);
            // Gleich anzeigen
            System.out.flush();
        }

        System.out.printf("Done!");
    }
}
