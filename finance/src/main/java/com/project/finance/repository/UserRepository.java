package com.project.finance.repository;

import com.project.finance.entity.user;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<user, Long> {

    boolean existsByEmail(String email);

    Optional<user> findByEmail(String email);
}