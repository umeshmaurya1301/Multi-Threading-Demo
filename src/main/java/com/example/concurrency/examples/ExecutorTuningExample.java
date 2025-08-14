package com.example.concurrency.examples;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Component
public class ExecutorTuningExample implements DemoExample {
	@Override
	public String name() { return "executor-tuning"; }

	@Override
	public Object run() throws Exception {
		ThreadPoolExecutor ex = new ThreadPoolExecutor(
				2, 4,
				30, TimeUnit.SECONDS,
				new ArrayBlockingQueue<>(2),
				new ThreadPoolExecutor.CallerRunsPolicy());
		List<Future<Integer>> results = new ArrayList<>();
		for (int i = 0; i < 6; i++) {
			final int v = i;
			results.add(ex.submit(() -> v * v));
		}
		List<Integer> values = new ArrayList<>();
		for (Future<Integer> f : results) { values.add(f.get(1, TimeUnit.SECONDS)); }
		ex.shutdown();
		Map<String, Object> m = new LinkedHashMap<>();
		m.put("poolSize", ex.getPoolSize());
		m.put("values", values);
		return m;
	}
}
