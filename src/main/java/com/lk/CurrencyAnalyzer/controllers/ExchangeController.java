package com.lk.CurrencyAnalyzer.controllers;

import com.lk.CurrencyAnalyzer.models.User;
import com.lk.CurrencyAnalyzer.services.ExternalServices.RapidApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exchange")
public class ExchangeController {

    @GetMapping(path = "/test")
    public @ResponseBody String getAllUsers() {
        RapidApiService.currenciesList();
        return "Hellow wworld";
    }

}
