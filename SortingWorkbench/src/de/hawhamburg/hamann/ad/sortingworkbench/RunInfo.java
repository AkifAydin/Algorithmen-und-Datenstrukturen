package de.hawhamburg.hamann.ad.sortingworkbench;

import de.hawhamburg.hamann.ad.sortingworkbench.sorter.Sorter;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/**
 * Speichert Informationen zu einem Lauf mit <code>getNumElements</code>
 * Elementen der jeweiligen <code>Sorter</code>.
 * Die <code>SortingMetrics</code> können für die einzelnen Sorter
 * zu den unterschiedlichen Listenarten (random, ordered, reversed)
 * abgerufen werden.
 */
public class RunInfo {
    private final int numElements;

    private final Map<Sorter, RunMetrics> metrics;

    public RunInfo(List<Sorter> sorter, int numElements) {
        this.metrics = new IdentityHashMap<>(sorter.size());

        for (Sorter s : sorter) {
            metrics.put(s, new RunMetrics(s));
        }

        this.numElements = numElements;
    }

    public int getNumElements() {
        return numElements;
    }

    public SortingMetrics getRandomMetricsFor(Sorter sorter) {
        return metrics.get(sorter).getRandomMetrics();
    }

    public SortingMetrics getOrderedMetricsFor(Sorter sorter) {
        return metrics.get(sorter).getOrderedMetrics();
    }

    public SortingMetrics getReverseOrderedMetricsFor(Sorter sorter) {
        return metrics.get(sorter).getReverseOrderedMetrics();
    }
}
