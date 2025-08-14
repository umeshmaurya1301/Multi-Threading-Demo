package com.example.concurrency.example;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ThreadCreationExample {

    private int sharedCounter = 0;
    private final Object lock = new Object();

    public ResponseEntity<String> run() {
        StringBuilder result = new StringBuilder();
        
        try {
            // 1. Thread Creation Methods
            result.append("=== THREAD CREATION METHODS ===\n");
            
            result.append("\n1. EXTENDING THREAD CLASS:\n");
            demonstrateExtendingThread(result);
            
            result.append("\n2. IMPLEMENTING RUNNABLE INTERFACE:\n");
            demonstrateImplementingRunnable(result);
            
            result.append("\n3. LAMBDA EXPRESSIONS FOR THREADING:\n");
            demonstrateLambdaThreading(result);
            
            // 2. Race Condition Demonstration
            result.append("\n=== RACE CONDITION DEMONSTRATION ===\n");
            demonstrateRaceCondition(result);
            
            return ResponseEntity.ok(result.toString());
            
        } catch (Exception e) {
            log.error("Error in thread examples", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    // 1. EXTENDING THREAD CLASS
    private void demonstrateExtendingThread(StringBuilder result) throws InterruptedException {
        class CustomThread extends Thread {
            private final String threadName;
            private final int iterations;
            
            public CustomThread(String name, int iterations) {
                this.threadName = name;
                this.iterations = iterations;
                this.setName(threadName); // Set thread name for logging
            }
            
            @Override
            public void run() {
                log.info("{} started", threadName);
                for (int i = 1; i <= iterations; i++) {
                    log.info("{} - Iteration: {}/{}", threadName, i, iterations);
                    try {
                        Thread.sleep(200); // Simulate work
                    } catch (InterruptedException e) {
                        log.warn("{} was interrupted", threadName);
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                log.info("{} completed", threadName);
            }
        }
        
        // Create and start threads
        CustomThread thread1 = new CustomThread("ExtendedThread-1", 3);
        CustomThread thread2 = new CustomThread("ExtendedThread-2", 3);
        
        long startTime = System.currentTimeMillis();
        thread1.start();
        thread2.start();
        
        // Wait for completion
        thread1.join();
        thread2.join();
        
        long endTime = System.currentTimeMillis();
        result.append("Extending Thread Class - Both threads completed in ")
              .append(endTime - startTime).append("ms\n");
        result.append("Pros: Simple, direct control over thread lifecycle\n");
        result.append("Cons: Cannot extend other classes, tight coupling\n");
    }

    // 2. IMPLEMENTING RUNNABLE INTERFACE
    private void demonstrateImplementingRunnable(StringBuilder result) throws InterruptedException {
        class RunnableTask implements Runnable {
            private final String taskName;
            private final int iterations;
            
            public RunnableTask(String name, int iterations) {
                this.taskName = name;
                this.iterations = iterations;
            }
            
            @Override
            public void run() {
                log.info("{} started on thread: {}", taskName, Thread.currentThread().getName());
                for (int i = 1; i <= iterations; i++) {
                    log.info("{} - Iteration: {}/{}", taskName, i, iterations);
                    try {
                        Thread.sleep(150); // Simulate work
                    } catch (InterruptedException e) {
                        log.warn("{} was interrupted", taskName);
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                log.info("{} completed", taskName);
            }
        }
        
        // Create threads with Runnable tasks
        RunnableTask task1 = new RunnableTask("RunnableTask-1", 3);
        RunnableTask task2 = new RunnableTask("RunnableTask-2", 3);
        RunnableTask task3 = new RunnableTask("RunnableTask-3", 2);
        
        Thread thread1 = new Thread(task1, "Thread-Runnable-1");
        Thread thread2 = new Thread(task2, "Thread-Runnable-2");
        Thread thread3 = new Thread(task3, "Thread-Runnable-3");
        
        long startTime = System.currentTimeMillis();
        thread1.start();
        thread2.start();
        thread3.start();
        
        // Wait for completion
        thread1.join();
        thread2.join();
        thread3.join();
        
        long endTime = System.currentTimeMillis();
        result.append("Implementing Runnable - All threads completed in ")
              .append(endTime - startTime).append("ms\n");
        result.append("Pros: Can extend other classes, better separation of concerns, reusable\n");
        result.append("Cons: Slightly more verbose than extending Thread\n");
    }

    // 3. LAMBDA EXPRESSIONS FOR THREADING
    private void demonstrateLambdaThreading(StringBuilder result) throws InterruptedException {
        result.append("Lambda Threading Examples:\n");
        
        // Example 1: Simple Lambda Thread
        Thread lambdaThread1 = new Thread(() -> {
            String threadName = Thread.currentThread().getName();
            log.info("Lambda Thread {} started", threadName);
            for (int i = 1; i <= 3; i++) {
                log.info("Lambda Thread {} - Processing item {}", threadName, i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    log.warn("Lambda Thread {} interrupted", threadName);
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            log.info("Lambda Thread {} completed", threadName);
        }, "Lambda-Simple");
        
        // Example 2: Lambda with variable capture
        String taskType = "DataProcessing";
        int itemsToProcess = 4;
        
        Thread lambdaThread2 = new Thread(() -> {
            String threadName = Thread.currentThread().getName();
            log.info("Lambda Thread {} started for {}", threadName, taskType);
            for (int i = 1; i <= itemsToProcess; i++) {
                log.info("{} - {} item {}/{}", threadName, taskType, i, itemsToProcess);
                try {
                    Thread.sleep(120);
                } catch (InterruptedException e) {
                    log.warn("{} interrupted during {}", threadName, taskType);
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            log.info("{} completed {}", threadName, taskType);
        }, "Lambda-Capture");
        
        // Example 3: Method reference (alternative to lambda)
        Thread methodRefThread = new Thread(this::methodReferenceTask, "Lambda-MethodRef");
        
        long startTime = System.currentTimeMillis();
        lambdaThread1.start();
        lambdaThread2.start();
        methodRefThread.start();
        
        // Wait for completion
        lambdaThread1.join();
        lambdaThread2.join();
        methodRefThread.join();
        
        long endTime = System.currentTimeMillis();
        result.append("Lambda Threading - All threads completed in ")
              .append(endTime - startTime).append("ms\n");
        result.append("Pros: Concise, functional style, easy to read for simple tasks\n");
        result.append("Cons: Limited reusability, can become complex for larger tasks\n");
    }
    
    // Helper method for method reference example
    private void methodReferenceTask() {
        String threadName = Thread.currentThread().getName();
        log.info("Method Reference Thread {} executing", threadName);
        try {
            Thread.sleep(200);
            log.info("Method Reference Thread {} - Task completed", threadName);
        } catch (InterruptedException e) {
            log.warn("Method Reference Thread {} interrupted", threadName);
            Thread.currentThread().interrupt();
        }
    }

    // 4. RACE CONDITION DEMONSTRATION
    private void demonstrateRaceCondition(StringBuilder result) throws InterruptedException {
        result.append("Demonstrating Race Conditions:\n\n");
        
        // Reset counter
        sharedCounter = 0;
        
        final int numberOfThreads = 10;
        final int incrementsPerThread = 1000;
        final int expectedResult = numberOfThreads * incrementsPerThread;
        
        result.append("Test Setup:\n");
        result.append("- Number of threads: ").append(numberOfThreads).append("\n");
        result.append("- Increments per thread: ").append(incrementsPerThread).append("\n");
        result.append("- Expected final counter value: ").append(expectedResult).append("\n\n");
        
        // Create threads that will cause race condition
        List<Thread> threads = new ArrayList<>();
        
        for (int i = 1; i <= numberOfThreads; i++) {
            final int threadId = i;
            Thread thread = new Thread(() -> {
                log.info("Race Thread {} started incrementing", threadId);
                for (int j = 0; j < incrementsPerThread; j++) {
                    // This operation is NOT thread-safe and causes race condition
                    // It involves: read current value -> increment -> write back
                    sharedCounter++; 
                }
                log.info("Race Thread {} finished incrementing", threadId);
            }, "RaceThread-" + i);
            
            threads.add(thread);
        }
        
        // Start all threads simultaneously
        long startTime = System.currentTimeMillis();
        log.info("Starting all race condition threads...");
        for (Thread thread : threads) {
            thread.start();
        }
        
        // Wait for all threads to complete
        for (Thread thread : threads) {
            thread.join();
        }
        long endTime = System.currentTimeMillis();
        
        // Display results
        result.append("RACE CONDITION RESULTS:\n");
        result.append("- Expected counter value: ").append(expectedResult).append("\n");
        result.append("- Actual counter value: ").append(sharedCounter).append("\n");
        result.append("- Difference (lost increments): ").append(expectedResult - sharedCounter).append("\n");
        result.append("- Execution time: ").append(endTime - startTime).append("ms\n");
        result.append("- Race condition occurred: ").append(sharedCounter != expectedResult ? "YES" : "NO").append("\n\n");
        
        // Explanation
        result.append("WHY RACE CONDITION OCCURS:\n");
        result.append("1. The increment operation (counter++) is not atomic\n");
        result.append("2. It consists of three steps: READ -> INCREMENT -> WRITE\n");
        result.append("3. Multiple threads can execute these steps simultaneously\n");
        result.append("4. Thread A reads value 100, Thread B also reads 100\n");
        result.append("5. Both increment to 101 and write back\n");
        result.append("6. Result: Only one increment instead of two\n\n");
        
        // Demonstrate with synchronized version for comparison
        demonstrateSynchronizedCounter(result, numberOfThreads, incrementsPerThread);
    }
    
    // Helper method to show synchronized version
    private void demonstrateSynchronizedCounter(StringBuilder result, int numberOfThreads, int incrementsPerThread) throws InterruptedException {
        sharedCounter = 0;
        List<Thread> threads = new ArrayList<>();
        
        for (int i = 1; i <= numberOfThreads; i++) {
            final int threadId = i;
            Thread thread = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    synchronized (lock) {
                        sharedCounter++; // Now thread-safe
                    }
                }
            }, "SafeThread-" + i);
            
            threads.add(thread);
        }
        
        long startTime = System.currentTimeMillis();
        for (Thread thread : threads) {
            thread.start();
        }
        
        for (Thread thread : threads) {
            thread.join();
        }
        long endTime = System.currentTimeMillis();
        
        result.append("SYNCHRONIZED VERSION (NO RACE CONDITION):\n");
        result.append("- Final counter value: ").append(sharedCounter).append("\n");
        result.append("- Expected value: ").append(numberOfThreads * incrementsPerThread).append("\n");
        result.append("- Race condition occurred: NO\n");
        result.append("- Execution time: ").append(endTime - startTime).append("ms\n");
        result.append("- Note: Synchronized version is slower but thread-safe\n");
    }
}
