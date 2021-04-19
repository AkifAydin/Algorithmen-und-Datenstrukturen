package de.hawhamburg.hamann.ad.sortingworkbench.sorter;

import de.hawhamburg.hamann.ad.sortingworkbench.SortingMetrics;

import java.util.List;

/**
 * Dieses Interface muss für jeden Sortieralgorithmus der
 * in der Workbench laufen soll implementiert werden.
 */
public interface Sorter {
    /**
     * Liefert den Namen des Sortieralgorithmus.
     * @return Name des Algorithmus und ggf. kurze Zusatzinformationen
     */
    String getName();

    /**
     * Hier wird der Sortieralgorithmus implementiert.
     * Die zu sortierende Liste wird dabei in Place verändert.
     * Dazu kann auch die Methode <code>swap</code> verwendet werden.
     * @param toSort Die zu sortierende <code>Liste</code>
     * @param metrics Ein <code>SortingMetrics</code>-Objekt zu Sammeln von statistischen Informationen
     * @param <T> Typ der Elemente der Liste, der das Interface <code>Comparable</code> implementieren muss.
     */
    <T extends Comparable<T>> void sort(List<T> toSort, SortingMetrics metrics);

    /**
     * Tauscht das Element an der Position <code>e1</code> mit dem Element
     * an der Position <code>e2</code> in der übergebenen <code>Liste</code> <code>source</code>.
     * Die Anzahl der Vertauschungen wird in den übergebenen <code>SortingMetrics</code>
     * um eins erhöht.
     * @param source Die zu verändernde <code>Liste</code>
     * @param e1 Position des 1. Elements
     * @param e2 Position des 2. Elements
     * @param metrics <code>SortingMetrics</code> zu sammeln von statistischen Informationen zum Tauschen.
     * @param <T> Elementtyp der Liste.
     */
    default<T> void swap(List<T> source, int e1, int e2, SortingMetrics metrics) {

        T elemTmp = source.get(e1);
        source.set(e1, source.get(e2));
        source.set(e2, elemTmp);

        metrics.incrementMoves();
    }
}
