package com.example.concurrency.examples;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public class ReadWriteLockVsSynchronizedExample implements DemoExample {
	@Override
	public String name() { return "synchronized-vs-readwritelock"; }

	@Override
	public Object run() {
		Object monitor = new Object();
		int[] v = {0};
		synchronized (monitor) { v[0]++; }
		ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
		rw.writeLock().lock();
		try { v[0]++; } finally { rw.writeLock().unlock(); }
		Map<String, Object> m = new LinkedHashMap<>();
		m.put("value", v[0]);
		return m;
	}
}
