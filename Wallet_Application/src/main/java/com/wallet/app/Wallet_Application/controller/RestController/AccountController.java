package com.wallet.app.Wallet_Application.controller.RestController;

import com.wallet.app.Wallet_Application.daoRepository.UserRepository;
import com.wallet.app.Wallet_Application.entity.Account;
import com.wallet.app.Wallet_Application.entity.User;
import com.wallet.app.Wallet_Application.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
    private final UserRepository userRepo;

    @Autowired
    public AccountController(AccountService accountService, UserRepository userRepo) {
        this.accountService = accountService;
        this.userRepo = userRepo;
    }

    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@RequestBody AccountRequest accountRequest, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepo.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
//        create the account using the logged-in userId
        Account account = accountService.createAccount(user.getId(), accountRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAccounts(Authentication authentication) {
        String username = authentication.getName();
        List<Account> accounts = accountService.getAccountsForUser(username);

        return ResponseEntity.ok(accounts);
    }
}

