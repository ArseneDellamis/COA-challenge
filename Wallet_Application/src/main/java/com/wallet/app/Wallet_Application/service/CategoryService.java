package com.wallet.app.Wallet_Application.service;

import com.wallet.app.Wallet_Application.controller.DTO.CategoryRequest;
import com.wallet.app.Wallet_Application.daoRepository.CategoryRepository;
import com.wallet.app.Wallet_Application.daoRepository.UserRepository;
import com.wallet.app.Wallet_Application.entity.Category;
import com.wallet.app.Wallet_Application.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public Category createCategory(Authentication authentication,
                                   CategoryRequest categoryRequest) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (categoryRepository.findByNameAndUserId(categoryRequest.getName(), user.getId()).isPresent()) {
            throw new RuntimeException("Category already exists");
        }

        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setUser(user);

        return categoryRepository.save(category);
    }

    public List<Category> getCategoriesForAuthenticatedUser(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return categoryRepository.findByUserId(user.getId());
    }

    public Category validateCategoryForUser(Long userId, String categoryName) {
        return categoryRepository.findByNameAndUserId(categoryName, userId)
                .orElseThrow(() -> new RuntimeException("Category not found for the user: " + categoryName));
    }
}

