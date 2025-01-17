package com.wallet.app.Wallet_Application.controller.RestController;

import com.wallet.app.Wallet_Application.DTO.CategoryRequest;
import com.wallet.app.Wallet_Application.entity.Category;
import com.wallet.app.Wallet_Application.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<CategoryRequest> createCategory(@RequestBody CategoryRequest categoryRequest, Authentication authentication) {
        Category category = categoryService.createCategory(authentication, categoryRequest);
        CategoryRequest categoryDTO = new CategoryRequest(category.getId(), category.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryDTO);
    }

    @GetMapping
    public ResponseEntity<List<CategoryRequest>> getCategories(Authentication authentication) {
        List<Category> categories = categoryService.getCategoriesForAuthenticatedUser(authentication);
        List<CategoryRequest> categoryDTOs = categories.stream()
                .map(category -> new CategoryRequest(category.getId(), category.getName()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoryDTOs);
    }
}

