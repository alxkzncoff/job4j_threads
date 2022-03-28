package ru.job4j.pool;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class ParallelMergeSearchTest {

    @Test
    public void whenSearch5() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        assertThat(ParallelMergeSearch.search(array, 5), is(4));
    }

    @Test
    public void whenSearch2() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7};
        assertThat(ParallelMergeSearch.search(array, 2), is(1));
    }

    @Test
    public void whenSearchTable() {
        String[] array = {"Car", "House", "Road", "Air", "Plane", "Apple", "Couch", "Table", "Floor", "Cat", "Dog"};
        assertThat(ParallelMergeSearch.search(array, "Apple"), is(5));
    }

    @Test
    public void whenNoIndex() {
        Character[] array = {'a', 'b', 'c', 'd', 'e', 'f', 'g'};
        assertThat(ParallelMergeSearch.search(array, 'w'), is(-1));
    }

}