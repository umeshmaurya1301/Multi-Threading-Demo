package com.example.concurrency.examples;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.*;

@Component
public class FutureCallableExample implements DemoExample {
	@Override
	public String name() { return "future-callable"; }

	@Override
	public Object run() throws Exception {
		ExecutorService es = Executors.newSingleThreadExecutor();
		Future<Integer> f = es.submit(() -> 7);
		int v = f.get(1, TimeUnit.SECONDS);
		es.shutdown();
		Map<String, Object> m = new LinkedHashMap<>();
		m.put("value", v);
		return m;
	}
}
