package org.company.algospectra_backend.controller;

import lombok.RequiredArgsConstructor;
import org.company.algospectra_backend.service.AlgospectraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/guest")
@RequiredArgsConstructor
public class GuestController {

    private final AlgospectraService service;

    @PostMapping
    public ResponseEntity<?> guestLogin() {
        var guest = service.guestLogin();
        return ResponseEntity.ok(Map.of("guestId", guest.getId(), "name", guest.getName()));
    }
}
