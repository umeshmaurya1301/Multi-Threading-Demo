package com.example.concurrency.example;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class MonitorLockExample {

    public ResponseEntity<String> run() {
        StringBuilder result = new StringBuilder();

        try {
            result.append("=== MONITOR LOCK EXAMPLES ===\n\n");

            // 1. Basic Monitor Lock Example
            result.append("1. BASIC MONITOR LOCK BEHAVIOR:\n");
            demonstrateBasicMonitorLock(result);

            // 2. Different Monitor Objects
            result.append("\n2. DIFFERENT MONITOR OBJECTS:\n");
            demonstrateDifferentMonitors(result);

            // 3. Wait/Notify with Monitor Lock
            result.append("\n3. WAIT/NOTIFY WITH MONITOR LOCK:\n");
            demonstrateWaitNotify(result);

            // 4. Producer-Consumer with Monitor Lock
            result.append("\n5. PRODUCER-CONSUMER WITH MONITOR LOCK:\n");
            demonstrateProducerConsumer(result);

            return ResponseEntity.ok(result.toString());

        } catch (Exception e) {
            log.error("Error in monitor lock examples", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    // 1. BASIC MONITOR LOCK DEMONSTRATION
    private void demonstrateBasicMonitorLock(StringBuilder result) throws InterruptedException {
        MonitorObject monitorObject = new MonitorObject();

        Thread thread1 = new Thread(monitorObject::task1, "Thread-1");
        Thread thread2 = new Thread(monitorObject::task2, "Thread-2");
        Thread thread3 = new Thread(monitorObject::task3, "Thread-3");

        long startTime = System.currentTimeMillis();

        thread1.start();
        thread2.start();
        thread3.start();

        // Wait for all threads to complete
        thread1.join();
        thread2.join();
        thread3.join();

        long endTime = System.currentTimeMillis();

        result.append("Basic monitor lock test completed in ")
                .append(endTime - startTime).append("ms\n");
        result.append("Observation: task1 and task2 run sequentially (same monitor)\n");
        result.append("task3 runs independently (no synchronization)\n");
    }

    private static class MonitorObject {

        private synchronized void task1() {
            String threadName = Thread.currentThread().getName();
            log.info("task1 started by {}", threadName);
            try {
                Thread.sleep(2000); // Reduced sleep time for demo
            } catch (InterruptedException e) {
                log.error("task1 interrupted", e);
                Thread.currentThread().interrupt();
            }
            log.info("task1 completed by {}", threadName);
        }

        private synchronized void task2() {
            String threadName = Thread.currentThread().getName();
            log.info("task2 Before Synchronized by {}", threadName);
            synchronized (this) {
                try {
                    log.info("task2 synchronized block started by {}", threadName);
                    Thread.sleep(2000); // Reduced sleep time for demo
                } catch (InterruptedException e) {
                    log.error("task2 interrupted", e);
                    Thread.currentThread().interrupt();
                }
            }
            log.info("task2 completed by {}", threadName);
        }

        private void task3() {
            String threadName = Thread.currentThread().getName();
            log.info("Task 3 started by {}", threadName);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            log.info("Task 3 completed by {}", threadName);
        }
    }

    // 2. DIFFERENT MONITOR OBJECTS
    private void demonstrateDifferentMonitors(StringBuilder result) throws InterruptedException {
        DifferentMonitorsExample example = new DifferentMonitorsExample();

        Thread t1 = new Thread(example::methodWithLock1, "Lock1-Thread");
        Thread t2 = new Thread(example::methodWithLock2, "Lock2-Thread");
        Thread t3 = new Thread(example::methodWithLock1, "Lock1-Thread2");

        long startTime = System.currentTimeMillis();

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        long endTime = System.currentTimeMillis();

        result.append("Different monitors test completed in ")
                .append(endTime - startTime).append("ms\n");
        result.append("Observation: lock1 and lock2 methods can run simultaneously\n");
        result.append("Multiple threads on same lock run sequentially\n");
    }

    private static class DifferentMonitorsExample {
        private final Object lock1 = new Object();
        private final Object lock2 = new Object();

        public void methodWithLock1() {
            synchronized (lock1) {
                String threadName = Thread.currentThread().getName();
                log.info("Method with lock1 started by {}", threadName);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                log.info("Method with lock1 completed by {}", threadName);
            }
        }

        public void methodWithLock2() {
            synchronized (lock2) {
                String threadName = Thread.currentThread().getName();
                log.info("Method with lock2 started by {}", threadName);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                log.info("Method with lock2 completed by {}", threadName);
            }
        }
    }

    // 3. WAIT/NOTIFY WITH MONITOR LOCK
    private void demonstrateWaitNotify(StringBuilder result) throws InterruptedException {
        WaitNotifyExample example = new WaitNotifyExample();

        Thread waiterThread = new Thread(() -> {
            try {
                example.waitForSignal();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Waiter-Thread");

        Thread notifierThread = new Thread(() -> {
            try {
                Thread.sleep(1000); // Wait before notifying
                example.sendSignal();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Notifier-Thread");

        long startTime = System.currentTimeMillis();

        waiterThread.start();
        notifierThread.start();

        waiterThread.join();
        notifierThread.join();

        long endTime = System.currentTimeMillis();

        result.append("Wait/Notify test completed in ")
                .append(endTime - startTime).append("ms\n");
        result.append("Observation: wait() releases lock, notify() signals waiting thread\n");
    }

    private static class WaitNotifyExample {
        private final Object monitor = new Object();
        private boolean signalReceived = false;

        public void waitForSignal() throws InterruptedException {
            synchronized (monitor) {
                String threadName = Thread.currentThread().getName();
                log.info("{} waiting for signal...", threadName);

                while (!signalReceived) {
                    monitor.wait(); // Releases the monitor lock and waits
                }

                log.info("{} received signal and proceeding", threadName);
            }
        }

        public void sendSignal() {
            synchronized (monitor) {
                String threadName = Thread.currentThread().getName();
                log.info("{} sending signal...", threadName);

                signalReceived = true;
                monitor.notify(); // Notifies one waiting thread

                log.info("{} signal sent", threadName);
            }
        }
    }


    // 4. PRODUCER-CONSUMER WITH MONITOR LOCK
    private void demonstrateProducerConsumer(StringBuilder result) throws InterruptedException {
        ProducerConsumerExample example = new ProducerConsumerExample();

        Thread producer1 = new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                try {
                    example.produce("Item-" + i);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "Producer-1");

        Thread consumer1 = new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                try {
                    example.consume();
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "Consumer-1");

        long startTime = System.currentTimeMillis();

        producer1.start();
        consumer1.start();

        producer1.join();
        consumer1.join();

        long endTime = System.currentTimeMillis();

        result.append("Producer-Consumer test completed in ")
                .append(endTime - startTime).append("ms\n");
        result.append("Observation: Coordinated access using wait/notify with monitor lock\n");
    }

    private static class ProducerConsumerExample {
        private final Object monitor = new Object();
        private String item = null;
        private boolean isItemAvailable = false;

        public void produce(String newItem) throws InterruptedException {
            synchronized (monitor) {
                while (isItemAvailable) {
                    log.info("Producer waiting - item already available");
                    monitor.wait(); // Wait until item is consumed
                }

                item = newItem;
                isItemAvailable = true;
                log.info("Produced: {}", item);

                monitor.notify(); // Notify consumer
            }
        }

        public void consume() throws InterruptedException {
            synchronized (monitor) {
                while (!isItemAvailable) {
                    log.info("Consumer waiting - no item available");
                    monitor.wait(); // Wait until item is produced
                }

                log.info("Consumed: {}", item);
                item = null;
                isItemAvailable = false;

                monitor.notify(); // Notify producer
            }
        }
    }
}
