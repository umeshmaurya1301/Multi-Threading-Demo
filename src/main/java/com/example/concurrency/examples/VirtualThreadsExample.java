package com.example.concurrency.examples;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Component
public class VirtualThreadsExample implements DemoExample {
	@Override
	public String name() { return "virtual-threads"; }

	@Override
	public Object run() throws Exception {
		try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
			List<Callable<String>> tasks = List.of(
					() -> "v1", () -> "v2", () -> "v3");
			List<Future<String>> futures = new ArrayList<>();
			for (Callable<String> t : tasks) futures.add(executor.submit(t));
			List<String> results = new ArrayList<>();
			for (Future<String> f : futures) results.add(f.get(1, TimeUnit.SECONDS));
			Map<String, Object> m = new LinkedHashMap<>();
			m.put("results", results);
			return m;
		}
	}
}
