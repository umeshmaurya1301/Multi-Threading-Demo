package com.example.concurrency.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "demo_entity")
public class DemoEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Instant createdAt = Instant.now();

	public DemoEntity() {}

	public DemoEntity(String name) {
		this.name = name;
	}

	public Long getId() { return id; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public Instant getCreatedAt() { return createdAt; }
}
