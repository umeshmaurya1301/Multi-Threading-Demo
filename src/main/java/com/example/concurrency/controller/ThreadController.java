package com.example.concurrency.controller;


import com.example.concurrency.example.MonitorLockExample;
import com.example.concurrency.example.ThreadCreationExample;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/thread")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ThreadController {

    ThreadCreationExample threadCreationExample;
    MonitorLockExample monitorlockExample;

    @PostMapping("/create")
    public ResponseEntity<String> threadCreation() {
        return threadCreationExample.run();
    }

    @PostMapping("/monitor-lock")
    public ResponseEntity<String> monitorLock() {
        return monitorlockExample.run();
    }
}
