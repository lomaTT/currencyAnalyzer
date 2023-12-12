package com.lk.CurrencyAnalyzer.models;

import com.lk.CurrencyAnalyzer.Enums.ECurrency;
import com.lk.CurrencyAnalyzer.Models.Currency;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CurrencyTest {

    @InjectMocks
    private Currency currency;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        currency = new Currency();
    }

    @Test
    public void givenCurrency_whenSetCurrency_thenGetCurrencyShouldReturnSameCurrency() {
        ECurrency expectedCurrency = ECurrency.CURRENCY_USD;

        currency.setCurrency(expectedCurrency);
        ECurrency retrievedCurrency = currency.getCurrencyName();

        assertEquals(expectedCurrency, retrievedCurrency);
    }
}