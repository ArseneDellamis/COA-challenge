package com.wallet.app.Wallet_Application.daoRepository;

import com.wallet.app.Wallet_Application.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByUserId(Long userId);

    Optional<Category> findByNameAndUserId(String name, Long userId);
}

