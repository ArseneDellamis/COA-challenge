package com.wallet.app.Wallet_Application.service;

import com.wallet.app.Wallet_Application.controller.DTO.TransactionRequest;
import com.wallet.app.Wallet_Application.daoRepository.AccountRepository;
import com.wallet.app.Wallet_Application.daoRepository.BudgetRepository;
import com.wallet.app.Wallet_Application.daoRepository.TransactionRepository;
import com.wallet.app.Wallet_Application.daoRepository.UserRepository;
import com.wallet.app.Wallet_Application.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final UserRepository userRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              AccountRepository accountRepository,
                              BudgetRepository budgetRepository,
                              CategoryRepository categoryRepository,
                              SubcategoryRepository subcategoryRepository,
                              UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.budgetRepository = budgetRepository;
        this.categoryRepository = categoryRepository;
        this.subcategoryRepository = subcategoryRepository;
        this.userRepository = userRepository;
    }

    public Transaction createTransaction(Authentication authentication, TransactionRequest transactionRequest) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Account account = accountRepository.findById(transactionRequest.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Ensure the account belongs to the logged-in user
        if (!account.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Account does not belong to the authenticated user");
        }

        Category category = null;
        if (transactionRequest.getCategoryId() != null) {
            category = categoryRepository.findById(transactionRequest.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
        }

        Subcategory subcategory = null;
        if (transactionRequest.getSubcategoryId() != null) {
            subcategory = subcategoryRepository.findById(transactionRequest.getSubcategoryId())
                    .orElseThrow(() -> new RuntimeException("Subcategory not found"));
        }

        Transaction transaction = new Transaction();
        transaction.setDescription(transactionRequest.getDescription());
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setDateTime(transactionRequest.getDateTime());
        transaction.setType(transactionRequest.getType());
        transaction.setAccount(account);
        transaction.setCategory(category);
        transaction.setSubcategory(subcategory);

        // Track the transaction amount for budget check
        double totalSpent = transactionRequest.getAmount();
        if (transactionRequest.getType() == TransactionType.EXPENSE) {
            totalSpent = -transactionRequest.getAmount();
        }

        // Check if the transaction exceeds budget
        checkBudgetExceed(user.getId(), totalSpent);

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionsForAccount(Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    public List<Transaction> generateReport(Authentication authentication, LocalDate startDate, LocalDate endDate) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return transactionRepository.findByUserIdAndDateTimeBetween(user.getId(), startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
    }

    private void checkBudgetExceed(Long userId, double totalSpent) {
        List<Budget> activeBudgets = budgetRepository.findByUserIdAndIsActive(userId, true);
        for (Budget budget : activeBudgets) {
            if (budget.getAmount() < totalSpent) {
                // Notify the user (send an alert or email, etc.)
                System.out.println("Budget limit exceeded: " + budget.getCategory());
            }
        }
    }
}
