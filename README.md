# concurrency-demo

Spring Boot 3.x (3.4.2) Java 21 concurrency demo project.

## Requirements
- Java 21 toolchain
- Spring Boot 3.4.2
- Gradle

## Run

```bash
./gradlew bootRun
```

## Endpoints

- List topics:

```bash
curl -s localhost:8080/demo
```

- Run a topic (examples):

```bash
curl -s localhost:8080/demo/thread-creation | jq
curl -s localhost:8080/demo/daemon-priority-join | jq
curl -s localhost:8080/demo/wait-notify | jq
curl -s localhost:8080/demo/reentrantlock-condition | jq
curl -s localhost:8080/demo/readwrite-stamped | jq
curl -s localhost:8080/demo/semaphores-latches-barriers | jq
curl -s localhost:8080/demo/executor-tuning | jq
curl -s localhost:8080/demo/completable-future | jq
curl -s localhost:8080/demo/forkjoin | jq
curl -s localhost:8080/demo/volatile-visibility | jq
curl -s localhost:8080/demo/atomic-longadder | jq
curl -s localhost:8080/demo/cas-aba | jq
curl -s localhost:8080/demo/concurrent-collections | jq
curl -s localhost:8080/demo/threadlocal | jq
curl -s localhost:8080/demo/virtual-threads | jq
curl -s localhost:8080/demo/transaction-rollback | jq
curl -s localhost:8080/demo/deadlock-demo | jq
```

- Thread dump (plain text):

```bash
curl -s localhost:8080/demo/thread-dump | head -200
```

## H2 Console

- Enabled at `/h2-console`
- JDBC URL: `jdbc:h2:mem:demo`
- Username: `sa`
- Password: (empty)

