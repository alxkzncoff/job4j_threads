package ru.job4j.concurrent;

/**
 * Пример работы с Threads.
 * @author Aleksandr Kuznetsov.
 * @version 1.0
 */

public class ConcurrentOutput {
    public static void main(String[] args) {
        Thread another = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        another.start();
        second.start();
        System.out.println(Thread.currentThread().getName());
    }
}
