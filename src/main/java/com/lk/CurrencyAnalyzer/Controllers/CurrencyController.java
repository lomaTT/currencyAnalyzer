package com.lk.CurrencyAnalyzer.Controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.lk.CurrencyAnalyzer.Models.Transaction;
import com.lk.CurrencyAnalyzer.Payload.Request.AddCurrencyRequest;
import com.lk.CurrencyAnalyzer.Payload.Request.DeleteCurrencyRequest;
import com.lk.CurrencyAnalyzer.Payload.Request.TradeCurrencyRequest;
import com.lk.CurrencyAnalyzer.Repositories.CurrencyRepository;
import com.lk.CurrencyAnalyzer.Repositories.TransactionRepository;
import com.lk.CurrencyAnalyzer.Repositories.UserRepository;
import com.lk.CurrencyAnalyzer.Repositories.UsersCurrenciesRepository;
import com.lk.CurrencyAnalyzer.services.CurrencyService;
import com.lk.CurrencyAnalyzer.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/currency")
public class CurrencyController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    UsersCurrenciesRepository usersCurrenciesRepository;

    @Autowired
    TransactionRepository transactionRepository;

    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/get/{base_currency}")
    private JsonNode getCurrencyInfo(@PathVariable String base_currency) {
        return currencyService.getCurrencyInfo(base_currency);
    }

    @GetMapping("/get")
    private JsonNode getCurrencyInfo() {
        return currencyService.getCurrencyInfo();
    }

    @GetMapping("/get-list-of-currencies")
    private String getListOfCurrencies() {
        return currencyService.getListOfCurrencies();
    }

    @PostMapping("/add-currency-to-currencies-list")
    private JsonNode addCurrencyToCurrenciesList(@Valid @RequestBody AddCurrencyRequest addCurrencyRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return currencyService.addCurrencyToCurrenciesList(userDetails, addCurrencyRequest);
    }

    @GetMapping("/get-users-currencies")
    private JsonNode getUsersCurrencies() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return currencyService.getUsersCurrencies(userDetails);
    }

    @DeleteMapping("/delete-user-currency")
    private JsonNode deleteUserCurrency(@Valid @RequestBody DeleteCurrencyRequest deleteCurrencyRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return currencyService.deleteUserCurrency(userDetails, deleteCurrencyRequest);
    }

    @GetMapping("/get-last-user-transactions")
    private List<Transaction> getLastUserTransaction() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return currencyService.getLastUserTransactions(userDetails);
    }

    @PostMapping("/trade-currency")
    private String tradeCurrency(@Valid @RequestBody TradeCurrencyRequest tradeCurrencyRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return currencyService.tradeCurrency(userDetails, tradeCurrencyRequest);
    }

}
