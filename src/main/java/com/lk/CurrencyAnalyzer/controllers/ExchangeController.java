package com.lk.CurrencyAnalyzer.controllers;

import com.lk.CurrencyAnalyzer.services.ExternalServices.RapidApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exchange")
public class ExchangeController {

    @GetMapping("/exchange/{from}/{to}")
    public String exchange(@PathVariable String from, @PathVariable String to) {
        return RapidApiService.exchangeValue(from,to);
    }
    
    @GetMapping("/initialise")
    public String test() {
        RapidApiService.currenciesList();

        return RapidApiService.currencies.toString();
    }

}
