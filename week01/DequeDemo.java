package com.algorithm018;

import java.util.ArrayDeque;
import java.util.Deque;

public class DequeDemo {
    public static void main(String[] args) {
        Deque<String> deque = new ArrayDeque<String>();
        // 在队列尾部新增元素
        System.out.println("deque add last");
        deque.addLast("a");
        System.out.println(deque);
        deque.addLast("b");
        System.out.println(deque);
        deque.addLast("c");
        System.out.println(deque);

        // 队列尾部元素
        String tail = deque.peekLast();
        System.out.println("last element: " + tail);
        System.out.println(deque);

        // 从队尾出队列
        System.out.println("deque poll last");
        while (deque.size() > 0) {
            System.out.println(deque.pollLast());
        }
        System.out.println(deque);

        // 在队列头部新增元素
        System.out.println("deque add first");
        deque.addFirst("1");
        System.out.println(deque);
        deque.addFirst("2");
        System.out.println(deque);

        // 队列头部元素
        String head = deque.peekFirst();
        System.out.println("first element: " + head);
        System.out.println(deque);

        // 从队列头部出队列
        System.out.println("deque poll first");
        while (deque.size() > 0) {
            System.out.println(deque.pollFirst());
            // or
//            System.out.println(deque.pop());
        }
        System.out.println(deque);
    }
}
