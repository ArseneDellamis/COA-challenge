package com.wallet.app.Wallet_Application.controller.RestController;

import com.wallet.app.Wallet_Application.controller.DTO.TransactionRequest;
import com.wallet.app.Wallet_Application.daoRepository.AccountRepository;
import com.wallet.app.Wallet_Application.daoRepository.UserRepository;
import com.wallet.app.Wallet_Application.entity.Account;
import com.wallet.app.Wallet_Application.entity.Transaction;
import com.wallet.app.Wallet_Application.entity.User;
import com.wallet.app.Wallet_Application.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Endpoint to create a transaction
    @PostMapping("/create")
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionRequest transactionRequest,
                                                         Authentication authentication) {
        // Pass the authenticated user to the service to create the transaction
        Transaction transaction = transactionService.createTransaction(authentication, transactionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }

    // Endpoint to get transactions for a specific account
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Transaction>> getTransactionsForAccount(@PathVariable Long accountId) {
        List<Transaction> transactions = transactionService.getTransactionsForAccount(accountId);
        return ResponseEntity.ok(transactions);
    }

    // Endpoint to generate a transaction report based on a time gap
    @GetMapping("/report")
    public ResponseEntity<List<Transaction>> generateReport(@RequestParam LocalDate startDate,
                                                            @RequestParam LocalDate endDate,
                                                            Authentication authentication) {
        List<Transaction> transactions = transactionService.generateReport(authentication, startDate, endDate);
        return ResponseEntity.ok(transactions);
    }
}


