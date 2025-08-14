package com.example.concurrency.examples;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.StampedLock;

@Component
public class StampedLockExample implements DemoExample {
	@Override
	public String name() { return "stampedlock"; }

	@Override
	public Object run() {
		StampedLock sl = new StampedLock();
		long s = sl.writeLock();
		try { /* mutate */ } finally { sl.unlockWrite(s); }
		long stamp = sl.tryOptimisticRead();
		boolean valid = sl.validate(stamp);
		Map<String, Object> m = new LinkedHashMap<>();
		m.put("optimisticValid", valid);
		return m;
	}
}
