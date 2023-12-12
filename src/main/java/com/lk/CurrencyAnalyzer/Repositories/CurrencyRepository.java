package com.lk.CurrencyAnalyzer.Repositories;

import com.lk.CurrencyAnalyzer.Enums.ECurrency;
import com.lk.CurrencyAnalyzer.Models.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Currency findByCurrency(ECurrency currency);
}
