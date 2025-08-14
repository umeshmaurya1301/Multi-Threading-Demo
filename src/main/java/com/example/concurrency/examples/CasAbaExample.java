package com.example.concurrency.examples;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

@Component
public class CasAbaExample implements DemoExample {
	@Override
	public String name() { return "cas-aba"; }

	@Override
	public Object run() throws Exception {
		AtomicReference<String> ref = new AtomicReference<>("A");
		AtomicStampedReference<String> stamped = new AtomicStampedReference<>("A", 0);

		Thread t1 = new Thread(() -> { ref.compareAndSet("A", "B"); ref.compareAndSet("B", "A"); });
		Thread t2 = new Thread(() -> {
			int[] st = new int[1]; String v = stamped.get(st);
			stamped.compareAndSet(v, "B", st[0], st[0] + 1);
			stamped.compareAndSet("B", "A", st[0] + 1, st[0] + 2);
		});
		t1.start(); t2.start();
		t1.join(); t2.join();

		boolean casWithNoStampThinksUnchanged = ref.compareAndSet("A", "C");
		int[] after = new int[1]; String finalVal = stamped.get(after);
		boolean stampedCasFromInitial = stamped.compareAndSet("A", "C", 0, 1);
		Map<String, Object> m = new LinkedHashMap<>();
		m.put("plainCASAssumesNoABA", casWithNoStampThinksUnchanged);
		m.put("stampedValue", finalVal);
		m.put("stampedVersion", after[0]);
		m.put("stampedCasFromInitialSucceeded", stampedCasFromInitial);
		return m;
	}
}
