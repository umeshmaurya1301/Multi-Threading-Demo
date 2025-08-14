package com.example.concurrency.examples;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class DaemonPriorityJoinExample implements DemoExample {
	@Override
	public String name() { return "daemon-priority-join"; }

	@Override
	public Object run() throws Exception {
		final int[] counter = {0};
		Thread daemon = new Thread(() -> {
			for (int i = 0; i < 1_000; i++) counter[0]++;
		});
		daemon.setDaemon(true);
		daemon.setPriority(Thread.MIN_PRIORITY);
		daemon.start();
		daemon.join(10);
		Map<String, Object> m = new LinkedHashMap<>();
		m.put("daemonAliveAfterJoin", daemon.isAlive());
		m.put("counter", counter[0]);
		return m;
	}
}
