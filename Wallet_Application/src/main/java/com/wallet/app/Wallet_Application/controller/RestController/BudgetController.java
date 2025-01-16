package com.wallet.app.Wallet_Application.controller.RestController;

import com.wallet.app.Wallet_Application.controller.DTO.BudgetRequest;
import com.wallet.app.Wallet_Application.daoRepository.UserRepository;
import com.wallet.app.Wallet_Application.entity.Budget;
import com.wallet.app.Wallet_Application.entity.User;
import com.wallet.app.Wallet_Application.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    private final BudgetService budgetService;
    private final UserRepository userRepository;

    @Autowired
    public BudgetController(BudgetService budgetService, UserRepository userRepository) {
        this.budgetService = budgetService;
        this.userRepository = userRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<Budget> createBudget(@RequestBody BudgetRequest budgetRequest, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Budget budget = budgetService.createBudget(user.getId(), budgetRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(budget);
    }

    @GetMapping
    public ResponseEntity<List<Budget>> getBudgets(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<Budget> budgets = budgetService.getBudgetsForUser(user.getId());
        return ResponseEntity.ok(budgets);
    }
}

