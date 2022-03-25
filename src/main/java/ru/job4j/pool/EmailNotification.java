package ru.job4j.pool;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Класс рассылки почты.
 * @author Aleksandr Kuznetsov.
 * @version 1.0
 */
public class EmailNotification {

    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    /**
     * Метод отправляет почту через ExecutorService.
     * @param user данные пользователя.
     */
    public void emailTo(User user) {
        pool.submit(() -> send(
                String.format("Notification %s to email %s", user.getUsername(), user.getEmail()),
                String.format("Add a new event to %s", user.getUsername()),
                user.getEmail())
        );
    }

    public void send(String subject, String body, String email) {

    }

    /**
     * Метод закрывает pool и ждет выполнения всех задач.
     */
    public void close() {
        pool.shutdown();
        while (pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
