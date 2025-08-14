package com.example.concurrency.examples;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
public class ThreadCreationExample implements DemoExample {

	private static int counter1 = 0;
	private static int counter2 = 0;

	@Override
	public String name() {
		return "thread-creation";
	}

	@Override
	public Object run() throws Exception {
		List<String> output = new ArrayList<>();

		/*
		 * 1️⃣ Thread creation by extending Thread
		 */
		class MyThread extends Thread {
			@Override
			public void run() {
				output.add("MyThread: running in " + Thread.currentThread().getName());
			}
		}
		Thread t1 = new MyThread();
		t1.start();
		t1.join(); // wait for it to finish

		/*
		 * 2️⃣ Thread creation by implementing Runnable
		 */
		class MyRunnable implements Runnable {
			@Override
			public void run() {
				output.add("MyRunnable: running in " + Thread.currentThread().getName());
			}
		}
		Thread t2 = new Thread(new MyRunnable());
		t2.start();
		t2.join();

		/*
		 * 3️⃣ Thread creation using Lambda Runnable (Java 8+)
		 */
		Thread t3 = new Thread(() ->
				output.add("Lambda Runnable: running in " + Thread.currentThread().getName())
		);
		t3.start();
		t3.join();

		/*
		 * 4️⃣ Thread creation using ExecutorService
		 */
		ExecutorService executor = Executors.newFixedThreadPool(2);
		List<Future<?>> futures = new ArrayList<>();

		futures.add(executor.submit(() ->
				output.add("ExecutorService Runnable: running in " + Thread.currentThread().getName())
		));
		futures.add(executor.submit(() -> {
			output.add("ExecutorService Callable: running in " + Thread.currentThread().getName());
			return null;
		}));

		// wait for tasks
		for (Future<?> f : futures) {
			f.get();
		}
		executor.shutdown();

		/*
		 * Return list of outputs
		 */
		raceConditionHandled();
		raceConditionHandledNotHandled();
		return output;
	}

	public static synchronized void increment() {
		counter1++;
	}
	public static void increment2() {
		counter2++;
	}

	private void raceConditionHandled() throws InterruptedException {
		Runnable task = () -> {
			for (int i = 0; i < 1000; i++) {
				increment();
			}
		};

		Thread thread1 = new Thread(task);
		Thread thread2 = new Thread(task);

		thread1.start();
		thread2.start();

		thread1.join();
		thread2.join();


		System.out.println("Expected counter value: 2000");
		System.out.println("Actual counter value: " + counter1);
	}

	private void raceConditionHandledNotHandled() throws InterruptedException {
		Runnable task = () -> {
			for (int i = 0; i < 1000; i++) {
				increment2();
			}
		};

		Thread thread1 = new Thread(task);
		Thread thread2 = new Thread(task);

		thread1.start();
		thread2.start();

		System.out.println("Expected counter value: 2000");
		System.out.println("Actual counter value: " + counter2);
	}
}

