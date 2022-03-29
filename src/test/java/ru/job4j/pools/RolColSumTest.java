package ru.job4j.pools;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class RolColSumTest {

    @Test
    public void whenSeq() {
        int[][] matrix = {{1, 2, 3}, {5, 6, 7}, {9, 10, 11}};
        RolColSum.Sums[] expected = {
                new RolColSum.Sums(6, 15),
                new RolColSum.Sums(18, 18),
                new RolColSum.Sums(30, 21)
        };
        assertThat(RolColSum.sum(matrix), is(expected));
    }

    @Test
    public void whenAsync() throws ExecutionException, InterruptedException {
        int[][] matrix = {{2, 4, 6}, {8, 10, 12}, {14, 16, 18}};
        RolColSum.Sums[] expected = {
                new RolColSum.Sums(12, 24),
                new RolColSum.Sums(30, 30),
                new RolColSum.Sums(48, 36)
        };
        assertThat(RolColSum.asyncSum(matrix), is(expected));
    }
}