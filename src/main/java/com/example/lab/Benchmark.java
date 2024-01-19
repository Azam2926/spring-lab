package com.example.lab;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Benchmark {
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Long timestamp = 1685698591924L;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Long timestamp2 = 1685698591924L;

    public static void main2(String[] args) {
        Benchmark benchmark = new Benchmark();
        long start = System.nanoTime();
        for (int i = 0; i < 1000000; i++) {
            benchmark.timestamp = System.currentTimeMillis();
        }
        long end = System.nanoTime();
        System.out.println("Time taken by @DateTimeFormat annotation: " + (end - start) / 1000000);

        start = System.nanoTime();
        for (int i = 0; i < 1000000; i++) {
            benchmark.timestamp2 = System.currentTimeMillis();
        }
        end = System.nanoTime();
        System.out.println("Time taken by @JsonFormat annotation: " + (end - start) / 1000000);
    }

    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

        // Thread 1 adds elements
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(2000);
                    queue.put(i);
                    System.out.println("Добавлен элемент " + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // Thread 2 extracts elements
        new Thread(() -> {
            while (true) {
                try {
                    Integer i = queue.take();
                    System.out.println("Извлечен элемент " + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}