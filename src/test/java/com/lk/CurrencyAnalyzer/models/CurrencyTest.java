package com.lk.CurrencyAnalyzer.models;

import com.lk.CurrencyAnalyzer.enums.ECurrency;
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
        // Given
        ECurrency expectedCurrency = ECurrency.CURRENCY_USD;

        // When
        currency.setCurrency(expectedCurrency);
        ECurrency retrievedCurrency = currency.getCurrencyName();

        // Then
        assertEquals(expectedCurrency, retrievedCurrency);
    }
}