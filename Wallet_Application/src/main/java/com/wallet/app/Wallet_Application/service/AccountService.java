package com.wallet.app.Wallet_Application.service;

import com.wallet.app.Wallet_Application.controller.RestController.AccountRequest;
import com.wallet.app.Wallet_Application.daoRepository.AccountRepository;
import com.wallet.app.Wallet_Application.daoRepository.UserRepository;
import com.wallet.app.Wallet_Application.entity.Account;
import com.wallet.app.Wallet_Application.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public Account createAccount(Long userId, AccountRequest AccountRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account account = new Account();
        account.setName(AccountRequest.getName());
        account.setType(AccountRequest.getType());
        account.setBalance(AccountRequest.getInitialBalance());
        account.setUser(user);

        return accountRepository.save(account);
    }

    public List<Account> getAccountsForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return accountRepository.findByUserId(user.getId());
    }
}

