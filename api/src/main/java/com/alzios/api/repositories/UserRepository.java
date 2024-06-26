package com.alzios.api.repositories;

import com.alzios.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
   User findByUsername(String username);

    Optional<User> findById(String userId);

    void deleteById(String userId);
}