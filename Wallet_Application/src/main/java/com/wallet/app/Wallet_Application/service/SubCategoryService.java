package com.wallet.app.Wallet_Application.service;

import com.wallet.app.Wallet_Application.controller.DTO.SubCategoryRequest;
import com.wallet.app.Wallet_Application.daoRepository.CategoryRepository;
import com.wallet.app.Wallet_Application.daoRepository.SubCategoryRepository;
import com.wallet.app.Wallet_Application.daoRepository.UserRepository;
import com.wallet.app.Wallet_Application.entity.Category;
import com.wallet.app.Wallet_Application.entity.Subcategory;
import com.wallet.app.Wallet_Application.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubCategoryService {

    private final SubCategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Autowired
    public SubCategoryService(SubCategoryRepository subcategoryRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.subcategoryRepository = subcategoryRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public Subcategory createSubcategory(Authentication authentication, SubCategoryRequest subcategoryRequest) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Category category = categoryRepository.findById(subcategoryRequest.getCategoryId())
                .filter(cat -> cat.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new RuntimeException("Category not found or does not belong to the user"));

        Subcategory subcategory = new Subcategory();
        subcategory.setName(subcategoryRequest.getName());
        subcategory.setCategory(category);

        return subcategoryRepository.save(subcategory);
    }

    public List<Subcategory> getSubcategoriesForAuthenticatedUser(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return subcategoryRepository.findByCategoryUserId(user.getId());
    }

}
