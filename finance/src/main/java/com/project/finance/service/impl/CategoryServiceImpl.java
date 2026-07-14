package com.project.finance.service.impl;

import com.project.finance.dto.CategoryRequest;
import com.project.finance.dto.CategoryResponse;
import com.project.finance.entity.Category;
import com.project.finance.entity.user;
import com.project.finance.repository.CategoryRepository;
import com.project.finance.repository.UserRepository;
import com.project.finance.service.impl.CategoryService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    public CategoryServiceImpl(
            CategoryRepository categoryRepository,
            UserRepository userRepository) {

        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }


    @Override
    public CategoryResponse createCategory(
            CategoryRequest categoryRequest,
            String email) {

        user user = getUserByEmail(email);

        boolean categoryExists =
                categoryRepository.existsByNameAndTypeAndUserId(
                        categoryRequest.getName(),
                        categoryRequest.getType(),
                        user.getId()
                );

        if (categoryExists) {

            throw new RuntimeException(
                    "Category already exists"
            );
        }

        Category category = new Category();

        category.setName(categoryRequest.getName());

        category.setType(categoryRequest.getType());

        category.setUser(user);

        Category savedCategory =
                categoryRepository.save(category);

        return convertToResponse(savedCategory);
    }


    @Override
    public List<CategoryResponse> getAllCategories(
            String email) {

        user user = getUserByEmail(email);

        return categoryRepository
                .findByUserId(user.getId())
                .stream()
                .map(this::convertToResponse)
                .toList();
    }


    @Override
    public CategoryResponse getCategoryById(
            Long categoryId,
            String email) {

        user user = getUserByEmail(email);

        Category category = categoryRepository
                .findByIdAndUserId(
                        categoryId,
                        user.getId()
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Category not found"
                        )
                );

        return convertToResponse(category);
    }


    @Override
    public CategoryResponse updateCategory(
            Long categoryId,
            CategoryRequest categoryRequest,
            String email) {

        user user = getUserByEmail(email);

        Category category = categoryRepository
                .findByIdAndUserId(
                        categoryId,
                        user.getId()
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Category not found"
                        )
                );

        category.setName(categoryRequest.getName());

        category.setType(categoryRequest.getType());

        Category updatedCategory =
                categoryRepository.save(category);

        return convertToResponse(updatedCategory);
    }


    @Override
    public String deleteCategory(
            Long categoryId,
            String email) {

        user user = getUserByEmail(email);

        Category category = categoryRepository
                .findByIdAndUserId(
                        categoryId,
                        user.getId()
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Category not found"
                        )
                );

        categoryRepository.delete(category);

        return "Category deleted successfully";
    }


    private user getUserByEmail(String email) {

        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );
    }


    private CategoryResponse convertToResponse(
            Category category) {

        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getType()
        );
    }
}