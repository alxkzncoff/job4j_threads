package ru.job4j;

import org.junit.Test;
import ru.job4j.buffer.SimpleBlockingQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

public class SimpleBlockingQueueTest {

    @Test
    public void offerThenPoll() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        List<Integer> result = new ArrayList<>();
        Thread offerTread = new Thread(() -> {
            for (int i = 1; i <= 6; i++) {
                try {
                    queue.offer(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread pollTread = new Thread(() -> {
            for (int i = 1; i <= 6; i++) {
                try {
                    result.add(queue.poll());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        offerTread.start();
        pollTread.start();
        offerTread.join();
        pollTread.join();
        assertThat(result, is(List.of(1, 2, 3, 4, 5, 6)));
    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 6; i++) {
                try {
                    queue.offer(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, is(List.of(1, 2, 3, 4, 5, 6)));
    }
}
