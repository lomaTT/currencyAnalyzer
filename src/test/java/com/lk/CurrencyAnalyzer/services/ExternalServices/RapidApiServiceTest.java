package com.lk.CurrencyAnalyzer.services.ExternalServices;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class RapidApiServiceTest {

    @BeforeAll
    public static void givenCurrenciesListIsLoaded() {
        RapidApiService.currenciesList();
    }

    @Test
    public void givenCurrenciesList_whenLoaded_thenListIsNotNull() {
        List<String> currencies = RapidApiService.currencies;
        assertNotNull(currencies);
    }

    @Test
    public void givenExchangeValues_whenRequested_thenResultIsNotNullAndNotEmpty() {
        String exchangeResult = RapidApiService.exchangeValue("USD", "EUR");
        assertNotNull(exchangeResult);
        assertFalse(exchangeResult.isEmpty());
    }
}