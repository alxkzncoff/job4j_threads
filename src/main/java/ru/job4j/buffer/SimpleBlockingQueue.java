package ru.job4j.buffer;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Реализация шаблона Producer Consumer с использованием wait/notify.
 * Producer помещает данные в очередь. Consumer извлекает данные из очереди.
 * Если очередь заполнена полностью, то при попытке добавления поток Producer блокируется,
 * до тех пор пока Consumer не извлечет очередные данные.
 * И наоборот если очередь пуста поток Consumer блокируется,
 * до тех пор пока Producer не поместит в очередь данные.
 *
 * @author Aleksandr Kuznetsov.
 * @version 1.0
 */

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int capacity;

    public SimpleBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Метод помещает данные в очередь, если она не полностью заполнена,
     * иначе поток блокируется, пока не освободится место.
     * @param value
     */
    public void offer(T value) throws InterruptedException {
        synchronized (this) {
            while (queue.size() == capacity) {
                wait();
            }
            queue.offer(value);
            notifyAll();
        }
    }

    /**
     * Метод забирает данные из очереди, если она не пуста,
     * иначе поток блокируется, пока в очередь не будут
     * помещены новые данные.
     * @return T объект очереди.
     */
    public T poll() throws InterruptedException {
        synchronized (this) {
            while (queue.isEmpty()) {
                wait();
            }
            T value  = queue.poll();
            notifyAll();
            return value;
        }
    }
}
