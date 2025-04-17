package org.company.algospectra_backend.repository;

import org.company.algospectra_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AlgospectraRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailId(String email);
}
