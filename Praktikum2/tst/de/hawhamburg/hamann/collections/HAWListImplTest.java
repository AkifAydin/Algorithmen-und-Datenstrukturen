package de.hawhamburg.hamann.collections;

import java.util.Comparator;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class HAWListImplTest {

    private static String[] NAMES = new String[]{"Bob", "Cyd", "Ada", "Ina", "Ivy", "Fed", "Dan", "Cyd", "Dee", "Cas"};

    private HAWList<String> createListWithNames() {
        HAWList<String> list = new HAWListImpl<>();

        for (String name : NAMES) {
            list.add(name);
        }

        return list;
    }
    @org.junit.jupiter.api.Test
    void add() {
        HAWList<String> list = new HAWListImpl<>();
        assertEquals(0, list.getSize());

        list.add("h");
        assertEquals(1, list.getSize());

        list.add("i");
        assertEquals(2, list.getSize());

        list = createListWithNames();
        assertEquals(NAMES.length, list.getSize());
    }

    @org.junit.jupiter.api.Test
    void sortSortsElements() {
        HAWList<String> list = createListWithNames();
        list.sort(Comparator.naturalOrder());

        Iterator<String> it = list.iterator();

        String prev = null;

        for (String s : list) {
            if (prev != null) {
                assertTrue(prev.compareTo(s) <= 0);
            }
            prev = s;
        }
    }

    @org.junit.jupiter.api.Test
    void sortKeepsElements() {

        HAWList<String> listOrg  = createListWithNames();
        HAWList<String> listSort = createListWithNames();

        listSort.sort(Comparator.naturalOrder());

        for (String s : listOrg) {
            assertTrue(listSort.contains(s), "Item is gone after search!");
        }
    }

    @org.junit.jupiter.api.Test
    void iterator() {
        HAWList<String> list = createListWithNames();
        int count = 0;

        for(String s : list) {
            count++;
        }

        assertEquals(list.getSize(), count);
    }
}