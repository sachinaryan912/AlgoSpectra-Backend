package org.company.algospectra_backend.service;

import lombok.RequiredArgsConstructor;
import org.company.algospectra_backend.model.User;
import org.company.algospectra_backend.repository.AlgospectraRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlgospectraService {

    private final AlgospectraRepository repo;
    private final PasswordEncoder passwordEncoder;

    public User register(String name, String email, String password) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setGuest(false);
        return repo.save(user);
    }

    public User guestLogin() {
        User guest = new User();
        guest.setName("Guest-" + UUID.randomUUID().toString().substring(0, 8));
        guest.setGuest(true);
        return repo.save(guest);
    }

    public Optional<User> getByEmail(String email) {
        return repo.findByEmail(email);
    }
}
