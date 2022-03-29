package ru.job4j.pools;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Класс считает сумму по строкам и столбцам квадратной матрице.
 * @author Aleksandr Kuznetsov.
 * @version 1.0
 */
public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }
    }

    /**
     * Метод считает сумму по строкам и столбцам последовательно.
     * @param matrix квадратная матрица.
     * @return массив объектов Sums.
     */
    public static Sums[] sum(int[][] matrix) {
        int n = matrix.length;
        Sums[] result = new Sums[n];
        int rowSum = 0;
        int colSum = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                rowSum += matrix[row][col];
                colSum += matrix[col][row];
            }
            result[row] = new Sums(rowSum, colSum);
            rowSum = 0;
            colSum = 0;
        }
        return result;
    }

    /**
     * Метод считает сумму по строкам и столбцам асинхронно.
     * @param matrix квадратная матрица.
     * @return массив объектов Sums.
     */
    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int n = matrix.length;
        Sums[] result = new Sums[n];
        for (int index = 0; index < n; index++) {
            result[index] = getTask(matrix, index).get();
        }
        return result;
    }

    public static CompletableFuture<Sums> getTask(int[][] data, int index) {
        return CompletableFuture.supplyAsync(() -> {
            int rowSum = 0;
            int colSum = 0;
            for (int i = 0; i < data.length; i++) {
                rowSum += data[index][i];
                colSum += data[i][index];
            }
            return new Sums(rowSum, colSum);
        });
    }
}
