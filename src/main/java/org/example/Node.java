package org.example;

public class Node<E> {
    final E key;
    final int hash;
    Node<E> next;

    public Node(E key, int hash, Node<E> next) {
        this.key = key;
        this.hash = hash;
        this.next = next;
    }
}
