package ru.job4j.concurrent.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Пример работы с Threads.
 * Программа скачивает файл из сети с ограничением по скорости.
 * @author Aleksandr Kuznetsov.
 * @version 1.0
 */

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String fileName;

    public Wget(String url, int speed, String fileName) {
        this.url = url;
        this.speed = speed;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long bytesWrite = 0;
            long startTime = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                bytesWrite += bytesRead;
                if (bytesWrite >= speed) {
                    bytesWrite = 0;
                    long deltaTime = System.currentTimeMillis() - startTime;
                    if (deltaTime < 1000) {
                        try {
                            Thread.sleep(1000 - deltaTime);
                            startTime = System.currentTimeMillis();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void argsValidate(int argsNum) {
        if (argsNum != 3) {
            throw new IllegalArgumentException("Parameters missed! Required three params: File path to download, "
                    + "download speed (byte per second) and file name to save. \n"
                    + "Example: https://site.com/file.txt 1048576 MyFile.txt");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        argsValidate(args.length);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String fileName = args[2];
        Thread wget = new Thread(new Wget(url, speed, fileName));
        wget.start();
        wget.join();
    }
}
