package de.hawhamburg.hamann.knapsack;

import java.util.*;

public class Main {

    static class Item {
        String name;
        int    size;
        int     val;

        public Item(String name, int size, int value) {
            this.name = name;
            this.size = size;
            this.val = value;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private static Item[] items;

    private static long count = 0;

    static {
        items = new Item[] {
                new Item( "A", 3, 4),
                new Item( "B", 4, 5),
                new Item( "C", 7, 10),
                new Item( "D", 8, 11),
                new Item( "E", 9, 13)
        };
    }

    public static void main(String[] args) {
        long start = System.nanoTime();

        int res = knap(17);

        long duration = (System.nanoTime() - start) / 1000000;

        System.out.format("Result: %d Calls: %,d Duration: %,dms", res, count, duration);
    }

    private static int knap(int cap) {
        count++;

        int i, space, max, t;

        for (i = 0, max = 0; i < items.length; i++) {
            space = cap - items[i].size;

            if (space >= 0) {
                t = knap(space) + items[i].val;

                if (t > max)
                    max = t;
            }
        }

        return max;
    }
}
