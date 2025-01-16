package com.wallet.app.Wallet_Application.daoRepository;

import com.wallet.app.Wallet_Application.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    List<Budget> findByUserId(Long userId);

    List<Budget> findByUserIdAndIsActive(Long userId, boolean isActive);
}
