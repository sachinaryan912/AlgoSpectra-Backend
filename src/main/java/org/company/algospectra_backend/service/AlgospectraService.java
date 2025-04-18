package org.company.algospectra_backend.service;

import lombok.RequiredArgsConstructor;
import org.company.algospectra_backend.model.User;
import org.company.algospectra_backend.repository.AlgospectraRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlgospectraService {

    private final AlgospectraRepository repo;
    private final BCryptPasswordEncoder passwordEncoder;  // BCrypt encoder

    // Register method with password hashing
    public User register(String name, String email, String password) {
        User user = new User();
        user.setUsername(name);
        user.setEmailId(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setGuest(false);
        return repo.save(user);
    }

    // Login method to compare the hashed password
    public Optional<User> login(String email, String password) {
        Optional<User> userOptional = repo.findByEmailId(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Compare the raw password with the hashed password
            if (passwordEncoder.matches(password, user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public Page<User> getAllUsers(Pageable pageable) {
        return repo.findAll(pageable);
    }



    public boolean deleteUserByEmail(String email) {
        Optional<User> userOpt = repo.findByEmailId(email);
        if (userOpt.isPresent()) {
            repo.delete(userOpt.get());
            return true;
        }
        return false;
    }


    // Get user by email
    public Optional<User> getByEmail(String email) {
        return repo.findByEmailId(email);
    }
}
