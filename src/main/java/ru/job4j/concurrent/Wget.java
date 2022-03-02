package ru.job4j.concurrent;

public class Wget {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    for (int i = 0; i < 100; i++) {
                        try {
                            Thread.sleep(100);
                            System.out.print("\rLoading : " + i  + "%");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("\rLoading : Done.");
                }
        );
        thread.start();
    }
}
