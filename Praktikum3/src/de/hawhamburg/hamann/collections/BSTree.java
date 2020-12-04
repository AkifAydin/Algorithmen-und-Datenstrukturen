package de.hawhamburg.hamann.collections;

public interface BSTree<K,E> {
    void add(K key, E e);

    void remove(K key);

    E search(K key);

    int size();

    boolean contains(K key);
}
