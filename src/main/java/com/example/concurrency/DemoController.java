package com.example.concurrency;

import com.example.concurrency.examples.*;
import com.example.concurrency.persistence.DemoTransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/demo")
public class DemoController {

	// Inject example classes that have dependencies
	@Autowired
	private TransactionRollbackExample transactionRollbackExample;

	// Individual endpoints for each concurrency example
	@GetMapping("/thread-creation")
	public ResponseEntity<Map<String, Object>> threadCreation() {
		try {
			ThreadCreationExample example = new ThreadCreationExample();
			Map<String, Object> result = new LinkedHashMap<>();
			result.put("topic", "thread-creation");
			result.put("data", example.run());
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			Map<String, Object> error = new LinkedHashMap<>();
			error.put("topic", "thread-creation");
			error.put("error", e.getClass().getSimpleName() + ": " + e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/atomic-longadder")
	public ResponseEntity<Map<String, Object>> atomicLongAdder() {
		try {
			AtomicAndAdderExample example = new AtomicAndAdderExample();
			Map<String, Object> result = new LinkedHashMap<>();
			result.put("topic", "atomic-longadder");
			result.put("data", example.run());
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			Map<String, Object> error = new LinkedHashMap<>();
			error.put("topic", "atomic-longadder");
			error.put("error", e.getClass().getSimpleName() + ": " + e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/cas-aba")
	public ResponseEntity<Map<String, Object>> casAba() {
		try {
			CasAbaExample example = new CasAbaExample();
			Map<String, Object> result = new LinkedHashMap<>();
			result.put("topic", "cas-aba");
			result.put("data", example.run());
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			Map<String, Object> error = new LinkedHashMap<>();
			error.put("topic", "cas-aba");
			error.put("error", e.getClass().getSimpleName() + ": " + e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/completable-future")
	public ResponseEntity<Map<String, Object>> completableFuture() {
		try {
			CompletableFutureExample example = new CompletableFutureExample();
			Map<String, Object> result = new LinkedHashMap<>();
			result.put("topic", "completable-future");
			result.put("data", example.run());
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			Map<String, Object> error = new LinkedHashMap<>();
			error.put("topic", "completable-future");
			error.put("error", e.getClass().getSimpleName() + ": " + e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/concurrent-collections")
	public ResponseEntity<Map<String, Object>> concurrentCollections() {
		try {
			ConcurrentCollectionsExample example = new ConcurrentCollectionsExample();
			Map<String, Object> result = new LinkedHashMap<>();
			result.put("topic", "concurrent-collections");
			result.put("data", example.run());
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			Map<String, Object> error = new LinkedHashMap<>();
			error.put("topic", "concurrent-collections");
			error.put("error", e.getClass().getSimpleName() + ": " + e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/daemon-priority-join")
	public ResponseEntity<Map<String, Object>> daemonPriorityJoin() {
		try {
			DaemonPriorityJoinExample example = new DaemonPriorityJoinExample();
			Map<String, Object> result = new LinkedHashMap<>();
			result.put("topic", "daemon-priority-join");
			result.put("data", example.run());
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			Map<String, Object> error = new LinkedHashMap<>();
			error.put("topic", "daemon-priority-join");
			error.put("error", e.getClass().getSimpleName() + ": " + e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/deadlock-demo")
	public ResponseEntity<Map<String, Object>> deadlockDemo() {
		try {
			DeadlockDemoExample example = new DeadlockDemoExample();
			Map<String, Object> result = new LinkedHashMap<>();
			result.put("topic", "deadlock-demo");
			result.put("data", example.run());
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			Map<String, Object> error = new LinkedHashMap<>();
			error.put("topic", "deadlock-demo");
			error.put("error", e.getClass().getSimpleName() + ": " + e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/executor-tuning")
	public ResponseEntity<Map<String, Object>> executorTuning() {
		try {
			ExecutorTuningExample example = new ExecutorTuningExample();
			Map<String, Object> result = new LinkedHashMap<>();
			result.put("topic", "executor-tuning");
			result.put("data", example.run());
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			Map<String, Object> error = new LinkedHashMap<>();
			error.put("topic", "executor-tuning");
			error.put("error", e.getClass().getSimpleName() + ": " + e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/forkjoin")
	public ResponseEntity<Map<String, Object>> forkJoin() {
		try {
			ForkJoinExample example = new ForkJoinExample();
			Map<String, Object> result = new LinkedHashMap<>();
			result.put("topic", "forkjoin");
			result.put("data", example.run());
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			Map<String, Object> error = new LinkedHashMap<>();
			error.put("topic", "forkjoin");
			error.put("error", e.getClass().getSimpleName() + ": " + e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/future-callable")
	public ResponseEntity<Map<String, Object>> futureCallable() {
		try {
			FutureCallableExample example = new FutureCallableExample();
			Map<String, Object> result = new LinkedHashMap<>();
			result.put("topic", "future-callable");
			result.put("data", example.run());
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			Map<String, Object> error = new LinkedHashMap<>();
			error.put("topic", "future-callable");
			error.put("error", e.getClass().getSimpleName() + ": " + e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/readwrite-stamped")
	public ResponseEntity<Map<String, Object>> readWriteStamped() {
		try {
			ReadWriteStampedLockExample example = new ReadWriteStampedLockExample();
			Map<String, Object> result = new LinkedHashMap<>();
			result.put("topic", "readwrite-stamped");
			result.put("data", example.run());
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			Map<String, Object> error = new LinkedHashMap<>();
			error.put("topic", "readwrite-stamped");
			error.put("error", e.getClass().getSimpleName() + ": " + e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/reentrantlock-condition")
	public ResponseEntity<Map<String, Object>> reentrantLockCondition() {
		try {
			ReentrantLockConditionExample example = new ReentrantLockConditionExample();
			Map<String, Object> result = new LinkedHashMap<>();
			result.put("topic", "reentrantlock-condition");
			result.put("data", example.run());
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			Map<String, Object> error = new LinkedHashMap<>();
			error.put("topic", "reentrantlock-condition");
			error.put("error", e.getClass().getSimpleName() + ": " + e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/semaphores-latches-barriers")
	public ResponseEntity<Map<String, Object>> semaphoresLatchesBarriers() {
		try {
			SemaphoresLatchesBarriersExample example = new SemaphoresLatchesBarriersExample();
			Map<String, Object> result = new LinkedHashMap<>();
			result.put("topic", "semaphores-latches-barriers");
			result.put("data", example.run());
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			Map<String, Object> error = new LinkedHashMap<>();
			error.put("topic", "semaphores-latches-barriers");
			error.put("error", e.getClass().getSimpleName() + ": " + e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/stampedlock")
	public ResponseEntity<Map<String, Object>> stampedLock() {
		try {
			StampedLockExample example = new StampedLockExample();
			Map<String, Object> result = new LinkedHashMap<>();
			result.put("topic", "stampedlock");
			result.put("data", example.run());
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			Map<String, Object> error = new LinkedHashMap<>();
			error.put("topic", "stampedlock");
			error.put("error", e.getClass().getSimpleName() + ": " + e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/synchronized-vs-readwritelock")
	public ResponseEntity<Map<String, Object>> synchronizedVsReadWriteLock() {
		try {
			ReadWriteLockVsSynchronizedExample example = new ReadWriteLockVsSynchronizedExample();
			Map<String, Object> result = new LinkedHashMap<>();
			result.put("topic", "synchronized-vs-readwritelock");
			result.put("data", example.run());
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			Map<String, Object> error = new LinkedHashMap<>();
			error.put("topic", "synchronized-vs-readwritelock");
			error.put("error", e.getClass().getSimpleName() + ": " + e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/threadlocal")
	public ResponseEntity<Map<String, Object>> threadLocal() {
		try {
			ThreadLocalExample example = new ThreadLocalExample();
			Map<String, Object> result = new LinkedHashMap<>();
			result.put("topic", "threadlocal");
			result.put("data", example.run());
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			Map<String, Object> error = new LinkedHashMap<>();
			error.put("topic", "threadlocal");
			error.put("error", e.getClass().getSimpleName() + ": " + e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/transaction-rollback")
	public ResponseEntity<Map<String, Object>> transactionRollback() {
		try {
			Map<String, Object> result = new LinkedHashMap<>();
			result.put("topic", "transaction-rollback");
			result.put("data", transactionRollbackExample.run());
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			Map<String, Object> error = new LinkedHashMap<>();
			error.put("topic", "transaction-rollback");
			error.put("error", e.getClass().getSimpleName() + ": " + e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/virtual-threads")
	public ResponseEntity<Map<String, Object>> virtualThreads() {
		try {
			VirtualThreadsExample example = new VirtualThreadsExample();
			Map<String, Object> result = new LinkedHashMap<>();
			result.put("topic", "virtual-threads");
			result.put("data", example.run());
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			Map<String, Object> error = new LinkedHashMap<>();
			error.put("topic", "virtual-threads");
			error.put("error", e.getClass().getSimpleName() + ": " + e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/volatile-visibility")
	public ResponseEntity<Map<String, Object>> volatileVisibility() {
		try {
			VolatileVisibilityExample example = new VolatileVisibilityExample();
			Map<String, Object> result = new LinkedHashMap<>();
			result.put("topic", "volatile-visibility");
			result.put("data", example.run());
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			Map<String, Object> error = new LinkedHashMap<>();
			error.put("topic", "volatile-visibility");
			error.put("error", e.getClass().getSimpleName() + ": " + e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/wait-notify")
	public ResponseEntity<Map<String, Object>> waitNotify() {
		try {
			WaitNotifyExample example = new WaitNotifyExample();
			Map<String, Object> result = new LinkedHashMap<>();
			result.put("topic", "wait-notify");
			result.put("data", example.run());
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			Map<String, Object> error = new LinkedHashMap<>();
			error.put("topic", "wait-notify");
			error.put("error", e.getClass().getSimpleName() + ": " + e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// List all available endpoints
	@GetMapping
	public Map<String, Object> listAllEndpoints() {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("endpoints", Arrays.asList(
			"GET /demo/thread-creation",
			"GET /demo/atomic-longadder",
			"GET /demo/cas-aba",
			"GET /demo/completable-future",
			"GET /demo/concurrent-collections",
			"GET /demo/daemon-priority-join",
			"GET /demo/deadlock-demo",
			"GET /demo/executor-tuning",
			"GET /demo/forkjoin",
			"GET /demo/future-callable",
			"GET /demo/readwrite-stamped",
			"GET /demo/reentrantlock-condition",
			"GET /demo/semaphores-latches-barriers",
			"GET /demo/stampedlock",
			"GET /demo/synchronized-vs-readwritelock",
			"GET /demo/threadlocal",
			"GET /demo/transaction-rollback",
			"GET /demo/virtual-threads",
			"GET /demo/volatile-visibility",
			"GET /demo/wait-notify",
			"GET /demo/thread-dump"
		));
		body.put("description", "All concurrency examples are now available as individual endpoints for easier navigation");
		return body;
	}

	@GetMapping("/thread-dump")
	public ResponseEntity<String> threadDump() {
		ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
		ThreadInfo[] infos = mxBean.dumpAllThreads(true, true);
		StringBuilder sb = new StringBuilder();
		for (ThreadInfo info : infos) {
			sb.append(info.toString());
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_PLAIN);
		return new ResponseEntity<>(sb.toString(), headers, HttpStatus.OK);
	}
}
