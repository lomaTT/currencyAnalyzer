package com.lk.CurrencyAnalyzer.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.lk.CurrencyAnalyzer.Models.Transaction;
import com.lk.CurrencyAnalyzer.Payload.Request.AddCurrencyRequest;
import com.lk.CurrencyAnalyzer.Payload.Request.DeleteCurrencyRequest;
import com.lk.CurrencyAnalyzer.Payload.Request.TradeCurrencyRequest;

import java.util.List;

public interface CurrencyService {
    JsonNode getCurrencyInfo(String baseCurrency);

    JsonNode getCurrencyInfo();

    String getListOfCurrencies();

    JsonNode addCurrencyToCurrenciesList(UserDetailsImpl userDetails, AddCurrencyRequest addCurrencyRequest);

    JsonNode getUsersCurrencies(UserDetailsImpl userDetails);

    JsonNode deleteUserCurrency(UserDetailsImpl userDetails, DeleteCurrencyRequest deleteCurrencyRequest);

    List<Transaction> getLastUserTransactions(UserDetailsImpl userDetails);

    String tradeCurrency(UserDetailsImpl userDetails, TradeCurrencyRequest tradeCurrencyRequest);
}