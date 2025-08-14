package com.example.concurrency.examples;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class ReentrantLockConditionExample implements DemoExample {
	@Override
	public String name() { return "reentrantlock-condition"; }

	@Override
	public Object run() throws Exception {
		ReentrantLock lock = new ReentrantLock();
		Condition condition = lock.newCondition();
		final boolean[] signaled = {false};

		Thread waiter = new Thread(() -> {
			lock.lock();
			try {
				try { condition.await(200, TimeUnit.MILLISECONDS); } catch (InterruptedException ignored) {}
				signaled[0] = true;
			} finally {
				lock.unlock();
			}
		});
		waiter.start();

		Thread signaler = new Thread(() -> {
			lock.lock();
			try {
				condition.signalAll();
			} finally {
				lock.unlock();
			}
		});
		signaler.start();
		waiter.join(300);
		Map<String, Object> m = new LinkedHashMap<>();
		m.put("signaled", signaled[0]);
		m.put("lockedFair", lock.isFair());
		return m;
	}
}
