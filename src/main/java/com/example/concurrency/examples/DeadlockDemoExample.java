package com.example.concurrency.examples;

import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class DeadlockDemoExample implements DemoExample {
	@Override
	public String name() { return "deadlock-demo"; }

	@Override
	public Object run() throws Exception {
		final Object a = new Object();
		final Object b = new Object();
		Thread t1 = new Thread(() -> { synchronized (a) { sleep(10); synchronized (b) { } } }, "Deadlock-1");
		Thread t2 = new Thread(() -> { synchronized (b) { sleep(10); synchronized (a) { } } }, "Deadlock-2");
		t1.setDaemon(true); t2.setDaemon(true);
		t1.start(); t2.start();
		Thread.sleep(50);
		ThreadMXBean bean = ManagementFactory.getThreadMXBean();
		long[] ids = bean.findDeadlockedThreads();
		Map<String, Object> m = new LinkedHashMap<>();
		m.put("deadlocked", ids != null ? ids.length : 0);
		if (ids != null) {
			ThreadInfo[] infos = bean.getThreadInfo(ids, true, true);
			m.put("threads", Arrays.stream(infos).map(ThreadInfo::getThreadName).toList());
		}
		return m;
	}

	private static void sleep(long ms) { try { Thread.sleep(ms); } catch (InterruptedException ignored) {} }
}
