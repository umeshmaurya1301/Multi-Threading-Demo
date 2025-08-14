package com.example.concurrency.examples;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class ConcurrentCollectionsExample implements DemoExample {
	@Override
	public String name() { return "concurrent-collections"; }

	@Override
	public Object run() {
		ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
		map.compute("x", (k, v) -> v == null ? 1 : v + 1);
		map.compute("x", (k, v) -> v == null ? 1 : v + 1);
		CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>(List.of(1,2,3));
		for (Integer i : list) { if (i == 2) list.add(4); }
		Map<String, Object> m = new LinkedHashMap<>();
		m.put("mapX", map.get("x"));
		m.put("list", list);
		return m;
	}
}
