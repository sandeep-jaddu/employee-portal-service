package com.example.employee.repository;

import com.example.employee.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByEmailId(String emailId);
}
