package com.project.finance.repository;

import com.project.finance.entity.Category;
import com.project.finance.enums.TransactionType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository
        extends JpaRepository<Category, Long> {

    List<Category> findByUserId(Long userId);

    Optional<Category> findByIdAndUserId(
            Long categoryId,
            Long userId
    );

    boolean existsByNameAndTypeAndUserId(
            String name,
            TransactionType type,
            Long userId
    );
}