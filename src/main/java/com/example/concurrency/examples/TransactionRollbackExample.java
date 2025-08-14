package com.example.concurrency.examples;

import com.example.concurrency.persistence.DemoTransactionalService;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class TransactionRollbackExample implements DemoExample {
	private final DemoTransactionalService service;

	public TransactionRollbackExample(DemoTransactionalService service) {
		this.service = service;
	}

	@Override
	public String name() { return "transaction-rollback"; }

	@Override
	public Object run() {
		long before = service.count();
		try {
			service.saveMaybeFail("fail-1", true);
		} catch (Exception ignored) {}
		long afterFail = service.count();
		service.saveMaybeFail("ok-1", false);
		long afterSuccess = service.count();
		Map<String, Object> m = new LinkedHashMap<>();
		m.put("before", before);
		m.put("afterFail", afterFail);
		m.put("afterSuccess", afterSuccess);
		return m;
	}
}
