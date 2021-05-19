package de.hawhamburg.hamann.collections;

import java.util.Comparator;
import java.util.Enumeration;

public interface HAWList<E> extends Iterable<E> {
    /**
     * Liefert die aktuelle Größe der Liste,
     * also die Anzahl der in ihr enthaltenen Elemente.
     * @return Anzahl der Elemente in der Liste
     */
    int getSize();

    /**
     * Fügt ein Element ans Ende der Liste ein.
     * @param item Das Element, das hinzugefügt werden soll.
     */
    void add(E item);

    /**
     * Sortiert die Liste anhand des übergebenen <code>Comperator</code>.
     * @param o Der <code>Comperator</code> zum Vergleich bei der Sortierung.
     */
    void sort(Comparator<E> o);

    /**
     * Sucht nach einem Element in der Liste und gibt dessen Position zurück.
     * Falls das Element mehr als einmal in der Liste enthalten ist, dann
     * wird die erste Position zurückgegeben.
     * Geprüft wird auf <code>equals</code>.
     *
     * @param item Das gesuchte Element
     * @return Position des Elements oder -1 falls das Element nicht enthalten ist.
     */
    int find(E item);

    /**
     * Prüft ob ein Element in der Liste enthalten ist.
     * @param item Das zu prüfende Element
     *
     * @return <code>true</code>, falls das Element in der Liste enthalten ist.
     *         <code>false</code>, falls nicht.
     */
    default boolean contains(E item) {
        return find(item) > -1;
    }
}
