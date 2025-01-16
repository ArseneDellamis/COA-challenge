package com.wallet.app.Wallet_Application.service;

import com.wallet.app.Wallet_Application.controller.DTO.BudgetRequest;
import com.wallet.app.Wallet_Application.daoRepository.BudgetRepository;
import com.wallet.app.Wallet_Application.daoRepository.UserRepository;
import com.wallet.app.Wallet_Application.entity.Budget;
import com.wallet.app.Wallet_Application.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;

    @Autowired
    public BudgetService(BudgetRepository budgetRepository, UserRepository userRepository) {
        this.budgetRepository = budgetRepository;
        this.userRepository = userRepository;
    }

    public Budget createBudget(Long userId, BudgetRequest budgetRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Budget budget = new Budget();
        budget.setAmount(budgetRequest.getAmount());
        budget.setCategory(budgetRequest.getCategory());
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

