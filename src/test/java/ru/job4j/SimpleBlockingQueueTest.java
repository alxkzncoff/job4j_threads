package ru.job4j;

import org.junit.Test;
import ru.job4j.buffer.SimpleBlockingQueue;

import java.util.ArrayList;
import java.util.List;

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
        Thread poolTread = new Thread(() -> {
            for (int i = 1; i <= 6; i++) {
                try {
                    result.add(queue.poll());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        offerTread.start();
        poolTread.start();
        offerTread.join();
        poolTread.join();
        assertThat(result, is(List.of(1, 2, 3, 4, 5, 6)));
    }
}
