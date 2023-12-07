package com.lk.CurrencyAnalyzer.repositories;

import com.lk.CurrencyAnalyzer.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {}
