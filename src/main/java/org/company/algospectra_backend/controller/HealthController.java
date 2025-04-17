package org.company.algospectra_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping
@RequiredArgsConstructor
public class HealthController {

    @GetMapping("/algohealth")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("API is healthy");
    }
}
