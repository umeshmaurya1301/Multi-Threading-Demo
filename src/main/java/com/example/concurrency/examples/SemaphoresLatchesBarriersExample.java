package com.example.concurrency.examples;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.*;

@Component
public class SemaphoresLatchesBarriersExample implements DemoExample {
	@Override
	public String name() { return "semaphores-latches-barriers"; }

	@Override
	public Object run() throws Exception {
		Semaphore sem = new Semaphore(2);
		CountDownLatch latch = new CountDownLatch(3);
		CyclicBarrier barrier = new CyclicBarrier(3);
		ExecutorService es = Executors.newFixedThreadPool(3);
		for (int i = 0; i < 3; i++) {
			final int idx = i;
			es.submit(() -> {
				try {
					sem.acquire();
					barrier.await(200, TimeUnit.MILLISECONDS);
				} catch (Exception ignored) {
				} finally {
					sem.release();
					latch.countDown();
				}
				return null;
			});
		}
		latch.await(1, TimeUnit.SECONDS);
		es.shutdownNow();
		Map<String, Object> m = new LinkedHashMap<>();
		m.put("availablePermits", sem.availablePermits());
		m.put("latchCount", latch.getCount());
		m.put("barrierParties", barrier.getParties());
		return m;
	}
}
