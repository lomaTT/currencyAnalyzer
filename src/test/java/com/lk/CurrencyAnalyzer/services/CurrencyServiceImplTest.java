package com.lk.CurrencyAnalyzer.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.lk.CurrencyAnalyzer.Enums.ECurrency;
import com.lk.CurrencyAnalyzer.Models.Currency;
import com.lk.CurrencyAnalyzer.Models.User;
import com.lk.CurrencyAnalyzer.Models.UsersCurrencies;
import com.lk.CurrencyAnalyzer.Payload.Request.AddCurrencyRequest;
import com.lk.CurrencyAnalyzer.Repositories.CurrencyRepository;
import com.lk.CurrencyAnalyzer.Repositories.TransactionRepository;
import com.lk.CurrencyAnalyzer.Repositories.UserRepository;
import com.lk.CurrencyAnalyzer.Repositories.UsersCurrenciesRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
public class CurrencyServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CurrencyRepository currencyRepository;

    @Mock
    private UsersCurrenciesRepository usersCurrenciesRepository;

    @InjectMocks
    private CurrencyServiceImpl currencyService;

    @Test
    public void givenBaseCurrency_whenGetCurrencyInfo_thenReturnValidJsonNode() {
        String baseCurrency = "USD";
        Mockito.when(currencyService.getCurrencyInfo(anyString())).thenReturn(Mockito.mock(JsonNode.class));

        JsonNode result = currencyService.getCurrencyInfo(baseCurrency);

        assertTrue(result.isObject());
        assertTrue(result.has("STATUS"));
        assertEquals("200", result.get("STATUS").asText());
    }

    @Test
    public void givenExistingCurrency_whenAddCurrencyToCurrenciesList_thenVerifyTransactionAndReturnSuccess() {
        AddCurrencyRequest addCurrencyRequest = new AddCurrencyRequest();
        addCurrencyRequest.setCurrency_id(ECurrency.CURRENCY_USD);
        addCurrencyRequest.setValue(100.0);

        User user = new User();
        user.setId(1L);

        Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(user));
        Mockito.when(currencyRepository.findByCurrency(any())).thenReturn(new Currency());
        Mockito.when(usersCurrenciesRepository.getUsersCurrenciesByCurrencyAndUser(any(), any())).thenReturn(Optional.of(new UsersCurrencies()));

        JsonNode result = currencyService.addCurrencyToCurrenciesList(Mockito.mock(UserDetailsImpl.class), addCurrencyRequest);

        assertTrue(result.isObject());
        assertTrue(result.has("status"));
        assertEquals("success", result.get("status").asText());
    }
}