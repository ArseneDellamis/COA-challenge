package com.wallet.app.Wallet_Application.daoRepository;

import com.wallet.app.Wallet_Application.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountId(Long accountId);

    List<Transaction> findByUserIdAndDateTimeBetween(Long userId, LocalDateTime startDateTime, LocalDateTime endDateTime);
}

