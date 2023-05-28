package org.example;

public class LinkedHash<E> {
    private final static int DefaultCapacity = 1 << 4;
    private final static float LoadFactor = 0.75f;
    Node<E>[] table;
    private int size;

    public LinkedHash() {
        table = (Node<E>[]) new Node[DefaultCapacity];
        size = 0;
    }

    // 보조 해시 함수 (상속 방지를 위해 private static final 선언)
    private static final int hash(Object key) {
        int hash;
        if (key == null) {
            return 0;
        } else {
            // hashCode()의 경우 음수가 나올 수 있으므로 절댓값을 통해 양수로 변환해준다.
            return Math.abs(hash = key.hashCode()) ^ (hash >>> 16);
        }
    }

    public void add(E key) {
        int hash = hash(key);
        int index = hash % table.length;

        if (table[index] == null) {
            table[index] = new Node<E>(key, hash, null);
        } else {
            Node<E> temp = table[index];
            Node<E> prev = null;

            while (temp != null) {
                if ((temp.hash == hash) && (temp.key == key)) {
                    break;
                }
                prev = temp;
                temp = temp.next;
            }

            prev.next = new Node<E>(key, hash, null);
        }
        size++;

        if (size >= LoadFactor * table.length) {
            resize();
        }
    }

    private void resize() {
        int newCapacity = table.length * 2;
        final Node<E>[] newTable = (Node<E>[]) new Node[newCapacity];

        for (int i = 0; i < table.length; i++) {
            Node<E> value = table[i];
            if (value == null) {
                continue;
            }

            table[i] = null;
            Node<E> nextNode;

            while (value != null) {
                int newIndex = value.hash % newCapacity;

                if (newTable[newIndex] != null) {
                    Node<E> tail = newTable[newIndex];
                    while (tail.next != null) {
                        tail = tail.next;
                    }

                    nextNode = value.next;
                    value.next = null;
                    tail.next = value;

                } else {
                    nextNode = value.next;
                    value.next = null;
                    newTable[newIndex] = value;
                }
                value = nextNode;
            }

        }
        table = null;
        table = newTable;
    }

    private Object remove(Object key) {
        int hash = hash(key);
        int index = hash % table.length;

        Node<E> node = table[index];
        Node<E> removedNode = null;
        Node<E> prev = null;

        if (node == null) {
            return null;
        }
        while (node != null) {
            if (node.key == key && node.hash == hash) {
                removedNode = node;
                if (prev == null) {
                    table[index] = node.next;
                    node = null;
                } else {
                    prev.next = node.next;
                    node = null;
                }
                size--;
                break;
            }
            prev = node;
            node = node.next;
        }
        return removedNode;
    }

    public Object get(Object key){
        int hash = hash(key);
        int index = hash % table.length;

        Node<E> node = table[index];
        while (node != null){
            if (node.hash == hash && (node.key == key || (key != null && key.equals(node.key)))) {
                return node.key;
            }
            node = node.next;
        }
        return null;
    }

    public int size() {
        return size;
    }

    public void clear() {
        if (table != null && size > 0) {
            for (int i = 0; i < table.length; i++) {
                table[i] = null;
            }
            size = 0;
        }
    }




}
