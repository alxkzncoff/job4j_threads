package ru.job4j.concurrent.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        long startTime;
        long endTime;
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")) {
            byte[] dataBuffer = new byte[speed];
            int bytesRead;
            startTime = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, speed)) != -1) {
                try {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                    endTime = System.currentTimeMillis();
                    if (endTime - startTime < 1000) {
                        Thread.sleep(1000 - (endTime - startTime));
                    }
                    startTime = System.currentTimeMillis();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
