package com.wallet.app.Wallet_Application.DTO;

import com.wallet.app.Wallet_Application.entity.AccountType;

public class AccountRequest {

    private String name;
    private AccountType type;
    private double initialBalance;

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(double initialBalance) {
        this.initialBalance = initialBalance;
    }
}
