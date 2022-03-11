package ru.job4j.concurrent.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Программа имитирующая банковский счет.
 * Функционал:
 *  - Добавление пользователей.
 *  - Обновление данных пользователей.
 *  - Удаление пользователей.
 *  - Трансфер денег от одного пользователя, другому.
 * @author Aleksandr Kuznetsov.
 * @version 1.0
 */

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();

    /**
     * Метод добавляет данные пользователя в хранилище, если такого пользователя
     * в хранилище еще нет.
     * @param user данные пользователя
     * @return True если пользователь успешно добавлен, иначе False.
     */
    public synchronized boolean add(User user) {
        boolean rsl = false;
        if (findById(user.getId()).isEmpty()) {
            users.put(user.getId(), user);
            rsl = true;
        }
        return rsl;
    }

    /**
     * Метод обновляет данные пользователя в хранилище, если такой пользователь
     * в хранилище есть.
     * @param user данные пользователя
     * @return True если данные успешно обновлены, иначе False.
     */
    public synchronized boolean update(User user) {
        boolean rsl = false;
        if (findById(user.getId()).isPresent()) {
            users.replace(user.getId(), user);
            rsl = true;
        }
        return rsl;
    }

    /**
     * Метод удаляет пользователя из хранилищя, если такой пользователь
     * в хранилище есть.
     * @param user данные пользователя
     * @return True если пользователь успешно удален, иначе False.
     */
    public synchronized boolean delete(User user) {
        boolean rsl = false;
        if (findById(user.getId()).isPresent()) {
            users.remove(user.getId());
            rsl = true;
        }
        return rsl;
    }

    private synchronized Optional<User> findById(int id) {
        Optional<User> rsl = Optional.empty();
        if (users.containsKey(id)) {
            rsl = Optional.of(users.get(id));
        }
        return rsl;
    }

    public synchronized Map<Integer, User> get() {
        return Map.copyOf(users);
    }

    /**
     * Метод переводит деньги от одного пользователя, другому.
     * @param fromId Пользователь, который переводит деньги.
     * @param toId Пользователь, который получает деньги.
     * @param amount Сумма перевода.
     */
    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        Optional<User> fromUser = findById(fromId);
        Optional<User> toUser = findById(toId);
        if (fromUser.isPresent() && toUser.isPresent()) {
            if (fromUser.get().getAmount() >= toUser.get().getAmount()) {
                fromUser.get().setAmount(fromUser.get().getAmount() - amount);
                toUser.get().setAmount(toUser.get().getAmount() + amount);
                rsl = true;
            }
        }
        return rsl;
    }
}
