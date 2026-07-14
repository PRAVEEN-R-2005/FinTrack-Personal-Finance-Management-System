package com.project.finance.service.impl;

import com.project.finance.dto.CategoryRequest;
import com.project.finance.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(
            CategoryRequest categoryRequest,
            String email
    );

    List<CategoryResponse> getAllCategories(
            String email
    );

    CategoryResponse getCategoryById(
            Long categoryId,
            String email
    );

    CategoryResponse updateCategory(
            Long categoryId,
            CategoryRequest categoryRequest,
            String email
    );

    String deleteCategory(
            Long categoryId,
            String email
    );
}