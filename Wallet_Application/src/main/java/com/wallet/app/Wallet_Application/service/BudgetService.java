package com.wallet.app.Wallet_Application.service;

import com.wallet.app.Wallet_Application.DTO.BudgetRequest;
import com.wallet.app.Wallet_Application.daoRepository.BudgetRepository;
import com.wallet.app.Wallet_Application.daoRepository.UserRepository;
import com.wallet.app.Wallet_Application.entity.Budget;
import com.wallet.app.Wallet_Application.entity.Category;
import com.wallet.app.Wallet_Application.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;
    private final CategoryService categoryService;

    @Autowired
    public BudgetService(BudgetRepository budgetRepository,
                         UserRepository userRepository,
                         CategoryService categoryService) {
        this.budgetRepository = budgetRepository;
        this.userRepository = userRepository;
        this.categoryService = categoryService;
    }

    public Budget createBudget(Authentication authentication, BudgetRequest budgetRequest) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Validate category
        Category category = categoryService.validateCategoryForUser(user.getId(), budgetRequest.getCategory());

        Budget budget = new Budget();
        budget.setAmount(budgetRequest.getAmount());
        budget.setCategory(category.getName());
        budget.setStartDate(budgetRequest.getStartDate());
        budget.setEndDate(budgetRequest.getEndDate());
        budget.setActive(budgetRequest.isActive());
        budget.setUser(user);

        return budgetRepository.save(budget);
    }

    public List<Budget> getBudgetsForUser(Long userId) {
        return budgetRepository.findByUserId(userId);
    }

    public void checkBudgetExceed(Long userId, double spentAmount) {
        List<Budget> activeBudgets = budgetRepository.findByUserIdAndIsActive(userId, true);

        for (Budget budget : activeBudgets) {
            if (spentAmount > budget.getAmount()) {
                // Send notification logic here (email, alert, etc.)
                System.out.println("Budget exceeded: " + budget.getCategory());
            }
        }
    }
}

