package ru.job4j.pool;

import ru.job4j.buffer.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

/**
 * Класс хранилище нитей - пул. Клиент берет нить из пула,
 * выполняет свою работу и возвращает нить обратно в пул.
 * @author Aleksandr Kuznetsov.
 * @version 1.0
 */

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(6);
    int size = Runtime.getRuntime().availableProcessors();

    public ThreadPool() {
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        tasks.poll().run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            threads.add(thread);
            thread.start();
        }
    }

    /**
     * Метод добавляет задачи в блокирующую очередь tasks.
     * @param job задача.
     */
    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    /**
     * Метод завершает все запущенные задачи.
     */
    public void shutdown() {
        for (Thread thread: threads) {
            thread.interrupt();
        }
    }
}
