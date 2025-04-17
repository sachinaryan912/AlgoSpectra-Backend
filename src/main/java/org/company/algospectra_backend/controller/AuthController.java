package org.company.algospectra_backend.controller;

import lombok.RequiredArgsConstructor;
import org.company.algospectra_backend.model.User;
import org.company.algospectra_backend.service.AlgospectraService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AlgospectraService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> payload) {
        var user = service.register(payload.get("name"), payload.get("email"), payload.get("password"));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
        Optional<User> user = service.login(payload.get("email"), payload.get("password"));

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        return ResponseEntity.ok("Login successful");
    }

    @GetMapping("/profile/{email}")
    public ResponseEntity<?> profile(@PathVariable String email) {
        return service.getByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}
