package com.example.concurrency.persistence;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DemoTransactionalService {
	private final DemoEntityRepository repository;

	public DemoTransactionalService(DemoEntityRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public Long saveMaybeFail(String name, boolean fail) {
		DemoEntity e = repository.save(new DemoEntity(name));
		if (fail) {
			throw new RuntimeException("forcing rollback");
		}
		return e.getId();
	}

	public long count() { return repository.count(); }
}
