package com.project.finance.controller;

import com.project.finance.dto.CategoryRequest;
import com.project.finance.dto.CategoryResponse;
import com.project.finance.service.impl.CategoryService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(
            CategoryService categoryService) {

        this.categoryService = categoryService;
    }


    // CREATE CATEGORY

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(
            @Valid @RequestBody CategoryRequest categoryRequest,
            Authentication authentication) {

        String email = authentication.getName();

        CategoryResponse response =
                categoryService.createCategory(
                        categoryRequest,
                        email
                );

        return ResponseEntity.ok(response);
    }


    // GET ALL CATEGORIES

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories(
            Authentication authentication) {

        String email = authentication.getName();

        List<CategoryResponse> categories =
                categoryService.getAllCategories(email);

        return ResponseEntity.ok(categories);
    }


    // GET CATEGORY BY ID

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> getCategoryById(
            @PathVariable Long categoryId,
            Authentication authentication) {

        String email = authentication.getName();

        CategoryResponse response =
                categoryService.getCategoryById(
                        categoryId,
                        email
                );

        return ResponseEntity.ok(response);
    }


    // UPDATE CATEGORY

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long categoryId,
            @Valid @RequestBody CategoryRequest categoryRequest,
            Authentication authentication) {

        String email = authentication.getName();

        CategoryResponse response =
                categoryService.updateCategory(
                        categoryId,
                        categoryRequest,
                        email
                );

        return ResponseEntity.ok(response);
    }


    // DELETE CATEGORY

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(
            @PathVariable Long categoryId,
            Authentication authentication) {

        String email = authentication.getName();

        String response =
                categoryService.deleteCategory(
                        categoryId,
                        email
                );

        return ResponseEntity.ok(response);
    }
}