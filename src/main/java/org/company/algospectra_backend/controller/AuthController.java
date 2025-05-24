package org.company.algospectra_backend.controller;

import lombok.RequiredArgsConstructor;
import org.company.algospectra_backend.model.User;
import org.company.algospectra_backend.ratelimiter.RateLimit;
import org.company.algospectra_backend.service.AlgospectraService;
import org.company.algospectra_backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AlgospectraService service;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/register")
    @RateLimit(limit = 5, duration = 1, timeUnit = TimeUnit.MINUTES)
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, String> payload) {
        User user = service.register(payload.get("name"), payload.get("email"), payload.get("password"));

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Registration successful");
        response.put("user", user);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    @RateLimit(limit = 5, duration = 1, timeUnit = TimeUnit.MINUTES)
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> payload) {
        Optional<User> userOpt = service.login(payload.get("email"), payload.get("password"));
        Map<String, Object> response = new HashMap<>();

        if (userOpt.isEmpty()) {
            response.put("status", "error");
            response.put("message", "User not found or invalid credentials");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        String token = jwtUtil.generateToken(payload.get("email"), String.valueOf(userOpt.get().getRole()));

        response.put("status", "success");
        response.put("message", "Login successful");
        response.put("access_token", token);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/guest-login")
    @RateLimit(limit = 100, duration = 1, timeUnit = TimeUnit.MINUTES)
    public ResponseEntity<?> guestLogin() {
        User guestUser = service.guestLogin();
        String token = jwtUtil.generateToken(guestUser.getEmailId(), String.valueOf(guestUser.getRole()));
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("username", guestUser.getUsername());
        response.put("role", guestUser.getRole());

        return ResponseEntity.ok(response);
    }



    @GetMapping("/profiles")
    @RateLimit(limit = 100, duration = 1, timeUnit = TimeUnit.MINUTES)
    public ResponseEntity<Map<String, Object>> getAllProfiles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<User> userPage = service.getAllUsers(pageable); // updated service method

        List<Map<String, Object>> userList = new ArrayList<>();
        for (User user : userPage.getContent()) {
            Map<String, Object> userData = new HashMap<>();
            userData.put("id", user.getId());
            userData.put("name", user.getUsername());
            userData.put("email", user.getEmailId());
            userData.put("userSince", user.getCreatedAt());
            userList.add(userData);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "User profiles retrieved");
        response.put("totalUsers", userPage.getTotalElements());
        response.put("currentPage", userPage.getNumber());
        response.put("totalPages", userPage.getTotalPages());
        response.put("users", userList);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/profile")
    @RateLimit(limit = 100, duration = 1, timeUnit = TimeUnit.MINUTES)
    public ResponseEntity<?> getUserProfile(@RequestParam String emailId, @RequestHeader("Authorization") String token) {

        String tokenEmail = jwtUtil.extractEmail(token.replace("Bearer ", ""));
        if (!tokenEmail.equals(emailId)) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "Unauthorized");
            response.put("message", "Unauthorized: You can only access your own profile.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        // Fetch user profile from the database
        Optional<User> userOpt = service.getUserByEmail(emailId);

        if (userOpt.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "not found");
            response.put("message", "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        User user = userOpt.get();
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "User profile fetched successfully");

        response.put("profile", user);

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/delete/{email}")
    @RateLimit(limit = 50, duration = 1, timeUnit = TimeUnit.MINUTES)
    public ResponseEntity<Map<String, Object>> deleteAccount(@PathVariable String email,@RequestHeader("Authorization") String token) {


        String tokenEmail = jwtUtil.extractEmail(token.replace("Bearer ", ""));
        if (!tokenEmail.equals(email)) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "Unauthorized");
            response.put("message", "Unauthorized: You can only delete  your own profile.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        boolean deleted = service.deleteUserByEmail(email);

        Map<String, Object> response = new HashMap<>();
        if (deleted) {
            response.put("status", "success");
            response.put("message", "Account deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            response.put("message", "User not found or could not delete");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

}
