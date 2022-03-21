package ru.job4j;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class CASCountTest {

    @Test
    public void incrementTwice() {
        CASCount casCount = new CASCount();
        casCount.increment();
        casCount.increment();
        assertThat(casCount.get(), is(2));
    }

    @Test
    public void twoThreadsIncrement() throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread first = new Thread(casCount::increment);
        Thread second = new Thread(casCount::increment);
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(casCount.get(), is(2));
    }

}