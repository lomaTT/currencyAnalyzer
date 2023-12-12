package com.lk.CurrencyAnalyzer.Repositories;

import com.lk.CurrencyAnalyzer.Models.Currency;
import com.lk.CurrencyAnalyzer.Models.User;
import com.lk.CurrencyAnalyzer.Models.UsersCurrencies;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersCurrenciesRepository extends JpaRepository<UsersCurrencies, Long> {
    Optional<UsersCurrencies> getUsersCurrenciesByCurrencyAndUser(Currency currency, User user);
    List<UsersCurrencies> findAllByUser(User user);

    Optional<UsersCurrencies> getUsersCurrenciesByUserIsAndCurrency(User user, Currency currency);
}
