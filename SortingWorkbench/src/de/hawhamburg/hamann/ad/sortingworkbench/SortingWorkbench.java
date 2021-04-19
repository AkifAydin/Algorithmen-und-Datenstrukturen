package de.hawhamburg.hamann.ad.sortingworkbench;

import de.hawhamburg.hamann.ad.sortingworkbench.sorter.*;
import de.hawhamburg.hamann.ad.sortingworkbench.util.ListValidator;
import de.hawhamburg.hamann.ad.sortingworkbench.util.ProgressEvent;
import de.hawhamburg.hamann.ad.sortingworkbench.util.WorkbenchProgressListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Das Herzstück des Projekts. Hier werden Sortieralgorithmen
 * konfigurierbar ausgeführt (<code>executeWorkbench</code>).
 *
 * Die Workbench wir mit einer Liste von <code>Sorter</code>n initialisiert.
 * Jeder Sorter steht für einen Sortieralgorithmus.
 *
 * Die Ausführung kann über die <code>WorkbenchConfig</code> dahingehend konfiguriert werden,
 * dass eine bestimme Anzahl an Läufen durchgeführt werden, die bei einer Grundzahl an zu
 * sortierenden Elementen starten und pro Schritt um eine definiert Anzahl an
 * Elementen erhöht werden.
 *
 * Bei jedem Schritt werden alle übergebenen Sortieralgorithmen für drei Listen ausgeführt:
 * 1. eine Liste mit Pseudozufallszahlen
 * 2. eine aufsteigend sortierte Liste
 * 3. eine absteigend sortierte Liste
 *
 * Die Ergebnisse eines Laufs können über einen Listener während der Ausführung ausgewertet werden.
 */
public class SortingWorkbench {

    /**
     * Liste der auszuführenden Sortieralgorithmen
     */
    private final List<Sorter> sorters;

    /**
     * Konfiguration der Workbench (Anzahl Läufe, Startanzahl an Elementen, ...)
     */
    private final WorkbenchConfig config;

    /**
     * Liste der Fortschritts-Listener
     */
    private final List<WorkbenchProgressListener> progressListener = new ArrayList<>();

    /**
     * Erzeugt eine neuen <code>SortingWorkbench</code> mit den auszuführenden
     * Sortieralgorithmen <code>sorters</code> und den Einstellungen in <code>config</code>.
     * @param sorters Die auszuführenden <Code>Sorter</Code>
     * @param config Einstellungen zu Ausführung
     */
    public SortingWorkbench(List<Sorter> sorters, WorkbenchConfig config) {
        this.sorters = sorters;
        this.config = config;
    }

    public void addProgressListener(WorkbenchProgressListener l) {
        this.progressListener.add(l);
    }

    public void removeProgressListener(WorkbenchProgressListener l) {
        this.progressListener.remove(l);
    }

    private void fireWorkbenchProgress(ProgressEvent event) {
        for (WorkbenchProgressListener l : this.progressListener) {
            l.reportProgress(event);
        }
    }

    public WorkbenchConfig getConfig() {
        return config;
    }

    public void executeWorkbench() {

        List<Integer> randomElements;
        List<Integer> orderedElements;
        List<Integer> reverseOrderedElements;
        List<Integer> toSort;

        DataRetriever dr = new DataRetriever();

        while (!this.getConfig().isFinished()) {
            RunInfo runConfig = this.config.getNextRunConfig();

            randomElements = dr.createRandomIntegerList(runConfig.getNumElements());
            orderedElements = dr.createOrderedIntegerList(runConfig.getNumElements());
            reverseOrderedElements = dr.createReverseOrderedIntegerList(runConfig.getNumElements());

            for (Sorter sorter : sorters) {
                toSort = new ArrayList<>(randomElements);
                sorter.sort(toSort, runConfig.getRandomMetricsFor(sorter));
                assert ListValidator.validateOrder(toSort) : "Randomized list was not sorted!";

                toSort = new ArrayList<>(orderedElements);
                sorter.sort(toSort, runConfig.getOrderedMetricsFor(sorter));
                assert ListValidator.validateOrder(toSort) : "Ordered list was not sorted!";

                toSort = new ArrayList<>(reverseOrderedElements);
                sorter.sort(toSort, runConfig.getReverseOrderedMetricsFor(sorter));
                assert ListValidator.validateOrder(toSort) : "Reverse ordered list was not sorted!";
            }

            this.fireWorkbenchProgress(new ProgressEvent(runConfig, "Run %05d with %d elements finished!".formatted(this.getConfig().getCurrentRun(), runConfig.getNumElements())));
        }
    }

    /**
     * Beispielausführung einer WorkBench.
     * Kann an die eigenen Bedürfnisse angepasst werden.
     * @param args Startargumente. Bisher nicht verwendet.
     */
    public static void main(String[] args) {
        List<Sorter> sorter = new ArrayList<>();
        sorter.add(new SelectionSort());
        sorter.add(new InsertionSort());
        sorter.add(new BubbleSort());
        sorter.add(new QuickSort(QuickSort.UsePivot.LEFT));
        sorter.add(new QuickSort(QuickSort.UsePivot.MID));
        sorter.add(new QuickSort(QuickSort.UsePivot.MEDIAN_OF_THREE));
        sorter.add(new QuickSort(QuickSort.UsePivot.RANDOM));

        WorkbenchConfig config = new WorkbenchConfig(sorter,100, 0, 50);

        SortingWorkbench wb = new SortingWorkbench(sorter, config);

        wb.addProgressListener(event -> {
            System.out.println(event.getMessage());

            for (Sorter s : sorter) {
                SortingMetrics metRandom = event.getRunConfig().getRandomMetricsFor(s);
                SortingMetrics metOrdered = event.getRunConfig().getOrderedMetricsFor(s);
                SortingMetrics metReverse = event.getRunConfig().getReverseOrderedMetricsFor(s);

                System.out.printf("  Results for %s:\n", s.getName());
                System.out.printf("    Swaps random: %d, ordered: %d, reverse: %d\n", metRandom.getNumMoves(), metOrdered.getNumMoves(), metReverse.getNumMoves());
                System.out.printf("    Compares random: %d, ordered: %d, reverse: %d\n", metRandom.getNumCompares(), metOrdered.getNumCompares(), metReverse.getNumCompares());
            }
        });

        wb.executeWorkbench();
    }
}
