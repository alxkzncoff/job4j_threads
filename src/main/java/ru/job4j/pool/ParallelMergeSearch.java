package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Класс осуществляет параллельный поиск индекса в массиве объектов,
 * те ищет нужный объект и возвращает его индекс.
 * @author Aleksand Kuznetsov.
 * @version 1.0
 */
public class ParallelMergeSearch<V> extends RecursiveTask<Integer> {

    private final V[] array;
    private final V target;
    private final int from;
    private final int to;

    public ParallelMergeSearch(V[] array, V target, int from, int to) {
        this.array = array;
        this.target = target;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (array.length <= 10) {
            for (int i = 0; i < array.length; i++) {
                if (array[i].equals(target)) {
                    return i;
                }
            }
        }
        int mid = (from + to) / 2;
        ParallelMergeSearch<V> leftSearch = new ParallelMergeSearch<>(array, target, from, mid);
        ParallelMergeSearch<V> rightSearch = new ParallelMergeSearch<>(array, target, mid + 1, to);
        leftSearch.fork();
        rightSearch.fork();
        Integer left = leftSearch.join();
        Integer right = rightSearch.join();
        if (left != null) {
            return left;
        } else if (right != null) {
            return right;
        }
        return null;
    }

    public Integer search(V[] array, V target) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelMergeSearch<>(array, target, 0, array.length));
    }
}
