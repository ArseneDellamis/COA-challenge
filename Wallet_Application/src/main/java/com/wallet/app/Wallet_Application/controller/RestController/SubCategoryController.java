package com.wallet.app.Wallet_Application.controller.RestController;

import com.wallet.app.Wallet_Application.controller.DTO.SubCategoryRequest;
import com.wallet.app.Wallet_Application.entity.Subcategory;
import com.wallet.app.Wallet_Application.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/subcategories")
public class SubCategoryController {

    private final SubCategoryService subcategoryService;

    @Autowired
    public SubCategoryController(SubCategoryService subcategoryService) {
        this.subcategoryService = subcategoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<SubCategoryRequest> createSubcategory(@RequestBody SubCategoryRequest subcategoryRequest, Authentication authentication) {
        Subcategory subcategory = subcategoryService.createSubcategory(authentication, subcategoryRequest);
        SubCategoryRequest subcategoryDTO = new SubCategoryRequest(subcategory.getId(), subcategory.getName(), subcategory.getCategory().getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(subcategoryDTO);
    }

    @GetMapping
    public ResponseEntity<List<SubCategoryRequest>> getSubcategories(Authentication authentication) {
        List<Subcategory> subcategories = subcategoryService.getSubcategoriesForAuthenticatedUser(authentication);
        List<SubCategoryRequest> subcategoryDTOs = subcategories.stream()
                .map(subcategory -> new SubCategoryRequest(subcategory.getId(), subcategory.getName(), subcategory.getCategory().getId()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(subcategoryDTOs);
    }
}

