package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Неблокирующий счетчик без синхронизации.
 * @author Aleksandr Kuznetsov.
 * @version 1.0
 */

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        int value;
        do {
            value = count.get();
        } while (!count.compareAndSet(value, value + 1));
    }

    public int get() {
        return count.get();
    }
}
