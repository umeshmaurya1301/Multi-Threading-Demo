package com.example.concurrency.examples;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

@Component
public class ForkJoinExample implements DemoExample {
	@Override
	public String name() { return "forkjoin"; }

	@Override
	public Object run() {
		int[] data = new int[100];
		for (int i = 0; i < data.length; i++) data[i] = i + 1;
		ForkJoinPool pool = ForkJoinPool.commonPool();
		class SumTask extends RecursiveTask<Long> {
			private final int start, end;
			SumTask(int s, int e) { this.start = s; this.end = e; }
			protected Long compute() {
				if (end - start <= 10) {
					long sum = 0; for (int i = start; i < end; i++) sum += data[i]; return sum;
				}
				int mid = (start + end) / 2;
				SumTask left = new SumTask(start, mid);
				SumTask right = new SumTask(mid, end);
				left.fork();
				return right.compute() + left.join();
			}
		}
		long sum = pool.invoke(new SumTask(0, data.length));
		Map<String, Object> m = new LinkedHashMap<>();
		m.put("sum", sum);
		return m;
	}
}
