package de.hawhamburg.hamann.collections;

import java.util.Comparator;
import java.util.Enumeration;

public interface HAWList<E> extends Iterable<E> {
    /**
     * Liefert die aktuelle Größe der Liste,
     * also die Anzahl der in ihr enthaltenen Elemente.
     * @return Anzahl der Elemente in der Liste
     */
    public int getSize();

    /**
     * Fügt ein Element ans Ende der Liste ein.
     * @param item
     */
    public void add(E item);

    /**
     * Sortiert die Liste anhand des übergebenen <code>Comperator</code>.
     * @param o Der <code>Comperator</code> zum Vergleich bei der Sortierung.
     */
    void sort(Comparator<E> o);
}
