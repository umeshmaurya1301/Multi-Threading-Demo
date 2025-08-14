package com.example.concurrency.examples;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

@Component
public class ReadWriteStampedLockExample implements DemoExample {
	@Override
	public String name() { return "readwrite-stamped"; }

	@Override
	public Object run() {
		ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
		ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
		StampedLock sl = new StampedLock();

		rw.writeLock().lock();
		try { map.put("a", 1); } finally { rw.writeLock().unlock(); }

		int optimisticRead;
		long stamp = sl.tryOptimisticRead();
		optimisticRead = map.getOrDefault("a", 0);
		if (!sl.validate(stamp)) {
			stamp = sl.readLock();
			try { optimisticRead = map.getOrDefault("a", 0); } finally { sl.unlockRead(stamp); }
		}
		Map<String, Object> m = new LinkedHashMap<>();
		m.put("value", optimisticRead);
		m.put("mapSize", map.size());
		return m;
	}
}
