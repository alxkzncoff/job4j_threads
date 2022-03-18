package ru.job4j.cache;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void whenAdd() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        base.setName("Base");
        cache.add(base);
        assertThat(cache.getMemory().get(1).getName(), is(base.getName()));
    }

    @Test
    public void whenUpdate() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        base.setName("Base");
        cache.add(base);
        base.setName("Updated base");
        cache.update(base);
        assertThat(cache.getMemory().get(1).getVersion(), is(2));
    }

    @Test(expected = OptimisticException.class)
    public void whenUpdateFail() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        Base updatedBase = new Base(1, 2);
        base.setName("Base");
        cache.add(base);
        cache.update(updatedBase);
    }

    @Test
    public void whenDelete() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        base.setName("Base");
        cache.add(base);
        cache.delete(base);
        assertTrue(cache.getMemory().isEmpty());
    }

}