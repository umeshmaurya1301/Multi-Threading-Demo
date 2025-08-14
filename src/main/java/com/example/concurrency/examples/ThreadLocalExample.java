package com.example.concurrency.examples;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

@Component
public class ThreadLocalExample implements DemoExample {
	@Override
	public String name() { return "threadlocal"; }

	static final ThreadLocal<String> REQUEST_ID = new ThreadLocal<>();

	static class ScopedRequest implements AutoCloseable {
		ScopedRequest(String id) { REQUEST_ID.set(id); }
		public void close() { REQUEST_ID.remove(); }
	}

	@Override
	public Object run() throws Exception {
		var exec = Executors.newFixedThreadPool(2);
		String id = UUID.randomUUID().toString();
		Callable<String> task = () -> REQUEST_ID.get();
		String v1, v2;
		try (ScopedRequest scope = new ScopedRequest(id)) {
			v1 = exec.submit(task).get();
			v2 = exec.submit(task).get();
		}
		exec.shutdown();
		Map<String, Object> m = new LinkedHashMap<>();
		m.put("valueSeen", v1);
		m.put("valueSeen2", v2);
		return m;
	}
}
