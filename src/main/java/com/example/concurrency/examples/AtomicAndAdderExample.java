package com.example.concurrency.examples;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

@Component
public class AtomicAndAdderExample implements DemoExample {
	@Override
	public String name() { return "atomic-longadder"; }

	@Override
	public Object run() throws Exception {
		AtomicInteger atomic = new AtomicInteger(0);
		LongAdder adder = new LongAdder();
		CountDownLatch latch = new CountDownLatch(2);
		Runnable task = () -> {
			for (int i = 0; i < 1_000; i++) {
				int prev, next;
				do { prev = atomic.get(); next = prev + 1; } while (!atomic.compareAndSet(prev, next));
				adder.increment();
			}
			latch.countDown();
		};
		new Thread(task).start();
		new Thread(task).start();
		latch.await(1, TimeUnit.SECONDS);
		Map<String, Object> m = new LinkedHashMap<>();
		m.put("atomic", atomic.get());
		m.put("adder", adder.sum());
		return m;
	}
}
