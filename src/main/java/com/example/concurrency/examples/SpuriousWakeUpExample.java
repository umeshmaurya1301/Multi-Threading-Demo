package com.example.concurrency.examples;

public class SpuriousWakeUpExample implements DemoExample {

    private final Object lock = new Object();
    private boolean conditionMet = false;

    @Override
    public String name() {
        return "spurious-wake-up";
    }

    @Override
    public Object run() throws Exception {
        Thread waitingThread = new Thread(() -> {
            synchronized (lock) {
                // Using a loop to handle spurious wake-ups safely
                while (!conditionMet) {
                    try {
                        System.out.println("Waiting thread: waiting...");
                        lock.wait();
                        System.out.println("Waiting thread: woke up, checking condition...");
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println("Waiting thread: interrupted");
                        return;
                    }
                }
                System.out.println("Waiting thread: condition met, proceeding.");
            }
        });

        waitingThread.start();

        // Sleep briefly to ensure waitingThread waits
        Thread.sleep(1000);

        // Spurious wake-up simulation: This thread will notify to wake the waiting thread
        // but in real scenarios, spurious wake-ups can occur without notify calls
        synchronized (lock) {
            System.out.println("Notifier thread: notifying...");
            conditionMet = true;
            lock.notify();
        }

        waitingThread.join();

        return "Spurious wake-up example completed";
    }
}

