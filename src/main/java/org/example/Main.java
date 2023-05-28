package org.example;

public class Main {
    public static void main(String[] args) {
        LinkedHash<String> linkedHash = new LinkedHash<>();

        linkedHash.add("apple");
        linkedHash.add("banana");
        linkedHash.add("cherry");

        System.out.println(linkedHash.get("banana"));  // "banana"에 해당하는 값을 조회하여 출력
        System.out.println(linkedHash.get("grape"));   // "grape"에 해당하는 값을 조회하여 출력 (없으므로 null 출력)
    }
}