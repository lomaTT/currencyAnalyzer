package com.lk.CurrencyAnalyzer.services.ExternalServices;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.List;

public class RapidApiService {

    public static List<String> currencies;

    @SneakyThrows
    public static void currenciesList() {
        com.mashape.unirest.http.HttpResponse<String> response = (com.mashape.unirest.http.HttpResponse<String>) Unirest.get("https://currency-exchange.p.rapidapi.com/listquotes")
                .header("X-RapidAPI-Key", "ec5de6e537msh1b66a4c5b439ac3p133e95jsnad0aebd13847")
                .header("X-RapidAPI-Host", "currency-exchange.p.rapidapi.com")
                .asString();

        System.out.println(response.getBody());
        currencies = Arrays.asList(response.getBody().split(","));
    }

    @SneakyThrows
    public static String exchangeValue(String from, String to) {
        HttpResponse<String> response = Unirest.get("https://currency-exchange.p.rapidapi.com/exchange?from="+from+"&to="+to+"&q=1.0")
                .header("X-RapidAPI-Key", "ec5de6e537msh1b66a4c5b439ac3p133e95jsnad0aebd13847")
                .header("X-RapidAPI-Host", "currency-exchange.p.rapidapi.com")
                .asString();

        return response.getBody();
    }
}
