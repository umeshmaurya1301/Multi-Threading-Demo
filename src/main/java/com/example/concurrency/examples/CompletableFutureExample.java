package com.example.concurrency.examples;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class CompletableFutureExample implements DemoExample {
	@Override
	public String name() { return "completable-future"; }

	@Override
	public Object run() throws Exception {
		var executor = Executors.newFixedThreadPool(2);
		try {
			CompletableFuture<Integer> a = CompletableFuture.supplyAsync(() -> 2, executor);
			CompletableFuture<Integer> b = CompletableFuture.supplyAsync(() -> 3, executor);
			int sum = a.thenCombine(b, Integer::sum).get(1, TimeUnit.SECONDS);
			Map<String, Object> m = new LinkedHashMap<>();
			m.put("sum", sum);
			return m;
		} finally {
			executor.shutdown();
		}
	}
}
