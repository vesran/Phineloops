package dev;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Testing {

    public static void main(String [] args) {
        PriorityQueue<Integer> stack = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer i, Integer j) {
                return -i.compareTo(j);
            }
        });

        stack.add(1);
        stack.add(2);
        stack.add(3);
        System.out.println(stack);

        System.out.println(stack.peek());
    }
}
