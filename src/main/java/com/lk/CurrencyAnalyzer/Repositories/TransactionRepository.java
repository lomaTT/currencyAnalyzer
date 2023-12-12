package com.lk.CurrencyAnalyzer.Repositories;

import com.lk.CurrencyAnalyzer.Models.Transaction;
import com.lk.CurrencyAnalyzer.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> getAllByUser(User user);
}
