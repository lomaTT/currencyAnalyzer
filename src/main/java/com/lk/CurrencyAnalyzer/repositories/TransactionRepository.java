package com.lk.CurrencyAnalyzer.repositories;

import com.lk.CurrencyAnalyzer.models.Transaction;
import com.lk.CurrencyAnalyzer.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> getAllByUser(User user);
}
