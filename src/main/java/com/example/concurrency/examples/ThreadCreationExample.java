package com.example.concurrency.examples;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.*;

@Component
public class ThreadCreationExample implements DemoExample {
	@Override
	public String name() { return "thread-creation"; }

	@Override
	public Object run() throws Exception {
		StringBuilder sb = new StringBuilder();
		Thread t = new Thread(() -> sb.append("hello from thread"));
		t.start();
		t.join();

		Runnable r = () -> {};
		Thread t2 = new Thread(r);
		t2.start();
		t2.join();

		ExecutorService es = Executors.newSingleThreadExecutor();
		Future<String> fut = es.submit(() -> "callable-result");
		String res = fut.get(1, TimeUnit.SECONDS);
		es.shutdown();
		Map<String, Object> m = new LinkedHashMap<>();
		m.put("threadMessage", sb.toString());
		m.put("callable", res);
		return m;
	}
}
