package ru.job4j.concurrent.storage;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

public class UserStorageTest {

    @Test
    public void add() {
        User user = new User(1, 100);
        UserStorage storage = new UserStorage();
        assertTrue(storage.add(user));
    }

    @Test
    public void addSameUserTwice() {
        User user = new User(1, 100);
        UserStorage storage = new UserStorage();
        storage.add(user);
        assertFalse(storage.add(user));
    }

    @Test
    public void remove() {
        User user = new User(1, 100);
        UserStorage storage = new UserStorage();
        storage.add(user);
        assertTrue(storage.delete(user));
    }

    @Test
    public void update() {
        User user = new User(1, 100);
        User updatedUser = new User(1, 150);
        UserStorage storage = new UserStorage();
        storage.add(user);
        assertTrue(storage.update(updatedUser));
    }

    @Test
    public void transfer() {
        UserStorage storage = new UserStorage();
        storage.add(new User(1, 150));
        storage.add(new User(2, 50));
        storage.transfer(1, 2, 50);
        assertThat(storage.get().get(1).getAmount(), is(100));
    }
}