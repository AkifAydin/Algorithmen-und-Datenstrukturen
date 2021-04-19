package de.hawhamburg.hamann.ad.sortingworkbench.util;

import java.util.List;

public  class ListValidator {
    private ListValidator() { }

    /**
     * Überprüft, ob eine Liste aufsteigend sortiert ist.
     *
     * @param toCheck Eine <code>Liste</code> mit Elementen die das Interface <code>Comparable</code> implementieren.
     * @param <E> Typ der Listenelemente. Muss <code>Comparable</code> implementieren.
     * @return <code>true</code>, falls die Liste aufsteigend sortiert ist. Sonst <code>false</code>.
     */
    public static <E extends Comparable<E>> boolean validateOrder(List<E> toCheck) {
        E e1;
        E e2;

        for (int i = 1; i < toCheck.size(); i++) {
            e1 = toCheck.get(i - 1);
            e2 = toCheck.get(i);

            if (e1.compareTo(e2) > 0) {
                return false;
            }
        }

        return true;
    }
}
