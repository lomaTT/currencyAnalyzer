package com.lk.CurrencyAnalyzer.repositories;

import com.lk.CurrencyAnalyzer.enums.ECurrency;
import com.lk.CurrencyAnalyzer.models.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Currency findByCurrency(ECurrency currency);
}
