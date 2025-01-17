package com.wallet.app.Wallet_Application.daoRepository;

import com.wallet.app.Wallet_Application.entity.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCategoryRepository extends JpaRepository<Subcategory, Long> {
    List<Subcategory> findByCategoryUserId(Long userId);
}
