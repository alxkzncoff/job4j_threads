package ru.job4j.concurrent;

/**
 * Пример работы с Threads.
 * Создается две дополнительные нити помимо основной (main).
 * Происходит вывод имен нитей в консоль. После завершения работы обеих нитей,
 * нить main выводит сообщение о завершении работы.
 * @author Aleksandr Kuznetsov.
 * @version 1.0
 */

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED
                || second.getState() != Thread.State.TERMINATED) {
            System.out.println(first.getState());
            System.out.println(second.getState());
            }
        System.out.println(first.getState());
        System.out.println(second.getState());
        System.out.println("Threads terminated.");
    }
}
