package org.company.algospectra_backend.controller;

import lombok.RequiredArgsConstructor;
import org.company.algospectra_backend.ratelimiter.RateLimit;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class HealthController {

    @GetMapping("/algohealth")
    @RateLimit(limit = 100, duration = 1, timeUnit = TimeUnit.MINUTES)
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("API is healthy");
    }
}
