package com.example.concurrency.problems.producer_consumer_exm;

import java.util.LinkedList;
import java.util.Queue;

class Buffer {
    private final int capacity;
    private final Queue<Integer> buffer;

    public Buffer(int capacity) {
        this.capacity = capacity;
        this.buffer = new LinkedList<>();
    }

    // Producer method to add data to buffer
    public synchronized void produce(int item) throws InterruptedException {
        // Wait while buffer is full
        while (buffer.size() == capacity) {
            System.out.println("Buffer is full. Producer is waiting...");
            wait(); // Release lock and wait
        }

        // Add item to buffer
        buffer.offer(item);
        System.out.println("Produced: " + item + " | Buffer size: " + buffer.size());

        // Notify waiting consumer
        notify();
    }

    // Consumer method to consume data from buffer
    public synchronized int consume() throws InterruptedException {
        // Wait while buffer is empty
        while (buffer.isEmpty()) {
            System.out.println("Buffer is empty. Consumer is waiting...");
            wait(); // Release lock and wait
        }

        // Remove item from buffer
        int item = buffer.poll();
        System.out.println("Consumed: " + item + " | Buffer size: " + buffer.size());

        // Notify waiting producer
        notify();
        return item;
    }
}
