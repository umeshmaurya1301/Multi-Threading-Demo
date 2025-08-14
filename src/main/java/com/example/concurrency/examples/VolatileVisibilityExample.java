package com.example.concurrency.examples;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class VolatileVisibilityExample implements DemoExample {
	@Override
	public String name() { return "volatile-visibility"; }

	@Override
	public Object run() throws Exception {
		class Runner { volatile boolean running = true; int iterations = 0; }
		Runner r = new Runner();
		Thread t = new Thread(() -> { while (r.running && r.iterations < 1_000_000) { r.iterations++; } });
		t.start();
		Thread.sleep(5);
		r.running = false;
		t.join(100);
		Map<String, Object> m = new LinkedHashMap<>();
		m.put("stopped", !t.isAlive());
		m.put("iterations", r.iterations);
		return m;
	}
}
