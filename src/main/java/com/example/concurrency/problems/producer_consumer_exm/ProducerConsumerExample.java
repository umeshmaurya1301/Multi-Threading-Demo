package com.example.concurrency.problems.producer_consumer_exm;

public class ProducerConsumerExample {

    public static void main(String[] args) {
        // Create buffer with capacity of 5
        Buffer buffer = new Buffer(5);

        // Create producer and consumer threads
        Thread producerThread = new Thread(new Producer(buffer), "Producer");
        Thread consumerThread = new Thread(new Consumer(buffer), "Consumer");

        // Start both threads
        producerThread.start();
        consumerThread.start();

        try {
            // Wait for both threads to complete
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Producer-Consumer execution completed!");
    }
}
