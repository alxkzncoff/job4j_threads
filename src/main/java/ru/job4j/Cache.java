package ru.job4j;

/**
 * Пример атомарности.
 * @author Aleksandr Kuznetsov.
 * @version 1.0
 */

public final class Cache {
    private static Cache cache;

    public synchronized static Cache instOf() {
        if (cache == null) {
            cache = new Cache();
        }
        return cache;
    }
}
