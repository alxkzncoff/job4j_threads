package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Неблокирующий кеш с помощью ConcurrentHashMap.
 * @author Aleksandr Kuznetsov.
 * @version 1.0
 */
public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (key, value) -> {
            if (model.getVersion() != value.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            Base result = new Base(key, model.getVersion() + 1);
            result.setName(model.getName());
            return result;
        }) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }

    public Map<Integer, Base> getMemory() {
        return memory;
    }
}
