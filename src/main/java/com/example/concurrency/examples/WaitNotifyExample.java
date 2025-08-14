package com.example.concurrency.examples;

import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class WaitNotifyExample implements DemoExample {
	@Override
	public String name() { return "wait-notify"; }

	@Override
	public Object run() throws Exception {
		Object lock = new Object();
		Deque<Integer> queue = new ArrayDeque<>();
		final int[] consumed = { -1 };

		Thread consumer = new Thread(() -> {
			synchronized (lock) {
				while (queue.isEmpty()) {
					try { lock.wait(200); } catch (InterruptedException ignored) { return; }
				}
				consumed[0] = queue.removeFirst();
			}
		});
		consumer.start();

		Thread producer = new Thread(() -> {
			synchronized (lock) {
				queue.addLast(42);
				lock.notifyAll();
			}
		});
		producer.start();
		producer.join();
		consumer.join(200);
		Map<String, Object> m = new LinkedHashMap<>();
		m.put("consumed", consumed[0]);
		return m;
	}
}
