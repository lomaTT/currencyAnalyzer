package com.lk.CurrencyAnalyzer.repositories;

import com.lk.CurrencyAnalyzer.enums.ECurrency;
import com.lk.CurrencyAnalyzer.models.Currency;
import com.lk.CurrencyAnalyzer.models.User;
import com.lk.CurrencyAnalyzer.models.UsersCurrencies;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersCurrenciesRepository extends JpaRepository<UsersCurrencies, Long> {
    Optional<UsersCurrencies> getUsersCurrenciesByCurrencyAndUser(Currency currency, User user);
}
