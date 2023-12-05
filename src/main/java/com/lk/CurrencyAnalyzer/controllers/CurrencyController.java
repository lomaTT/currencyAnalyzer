package com.lk.CurrencyAnalyzer.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.lk.CurrencyAnalyzer.enums.ECurrency;
import com.lk.CurrencyAnalyzer.models.Currency;
import com.lk.CurrencyAnalyzer.models.User;
import com.lk.CurrencyAnalyzer.models.UsersCurrencies;
import com.lk.CurrencyAnalyzer.payload.request.AddCurrencyRequest;
import com.lk.CurrencyAnalyzer.payload.request.DeleteCurrencyRequest;
import com.lk.CurrencyAnalyzer.repositories.CurrencyRepository;
import com.lk.CurrencyAnalyzer.repositories.UserRepository;
import com.lk.CurrencyAnalyzer.repositories.UsersCurrenciesRepository;
import com.lk.CurrencyAnalyzer.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials="true")
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

    private static final Logger logger = LoggerFactory.getLogger(CurrencyController.class);

    @GetMapping("/get/{base_currency}")
    private JsonNode getCurrencyInfo(@PathVariable String base_currency) {
        String base_url = "https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_HXoFpzn2SbXHwcvdZYd8QMWwV23L1AIsg2CMwB75&base_currency={0}";
        String result = MessageFormat.format(base_url, base_currency.toUpperCase());
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            URL url = new URL(result);
            JsonNode root = objectMapper.readTree(url);
            ((ObjectNode)root.get("data")).put("STATUS", "200");

            return root.get("data");
        } catch (Exception e) {
            logger.error("Cannot fetch currency data: {0}", e);

            ObjectNode jNode = objectMapper.createObjectNode();

            return jNode.put("STATUS", "404");
        }
    }

    @GetMapping("/get")
    private JsonNode getCurrencyInfo() {
        String base_url = "https://api.freecurrencyapi.com/v1/currencies?apikey=fca_live_HXoFpzn2SbXHwcvdZYd8QMWwV23L1AIsg2CMwB75";
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            URL url = new URL(base_url);
            JsonNode root = objectMapper.readTree(url);

            ObjectNode parsedValues = objectMapper.createObjectNode();

            for (JsonNode node: root.get("data")) {
                parsedValues.put(String.valueOf(node.get("name")).replaceAll("\"", ""), node.get("code"));
            }

            parsedValues.put("STATUS", "200");

            return parsedValues;
        } catch (Exception e) {
            logger.error("Cannot fetch currency data: {0}", e);

            ObjectNode jNode = objectMapper.createObjectNode();

            return jNode.put("STATUS", "404");
        }
    }

    @GetMapping("/get-list-of-currencies")
    private String getListOfCurrencies() {
        ArrayList<String> currenciesArrayToJson = new ArrayList<>();
        ECurrency[] currencies = ECurrency.class.getEnumConstants();
        for (ECurrency currency: currencies) {
            currenciesArrayToJson.add(currency.toString());
        }
        return new Gson().toJson(currenciesArrayToJson);
    }

    @PostMapping("/add-currency-to-currencies-list")
    private String addCurrencyToCurrenciesList(@Valid @RequestBody AddCurrencyRequest addCurrencyRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Currency currency = currencyRepository.findByCurrency(addCurrencyRequest.getCurrency_id());
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User with id " + userDetails.getId() + " not found"));

        if (usersCurrenciesRepository.getUsersCurrenciesByCurrencyAndUser(currency, user).isPresent()) {
            UsersCurrencies userCurrency = usersCurrenciesRepository.getUsersCurrenciesByCurrencyAndUser(currency, user).orElseThrow(() -> new UsernameNotFoundException("Query not found."));
            userCurrency.setValue(userCurrency.getValue() + addCurrencyRequest.getValue());
            usersCurrenciesRepository.save(userCurrency);
        } else {
            UsersCurrencies usersCurrencies = new UsersCurrencies(
                    user, currency, addCurrencyRequest.getValue()
            );

            usersCurrenciesRepository.save(usersCurrencies);
        }

        return SecurityContextHolder.getContext().toString();
    }

    @GetMapping("/get-users-currencies")
    private JsonNode getUsersCurrencies() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.createObjectNode();

        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User with such id not found!"));

        if (!usersCurrenciesRepository.findAllByUser(user).isEmpty()) {
            for (UsersCurrencies userCurrency : usersCurrenciesRepository.findAllByUser(user)) {
                ((ObjectNode) node).put(userCurrency.getCurrencyEnum().getCurrencyName().toString(), userCurrency.getValue());
            }
        }

        return node;
    }

    @DeleteMapping("/delete-user-currency")
    private JsonNode deleteUserCurrency(@Valid @RequestBody DeleteCurrencyRequest deleteCurrencyRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.createObjectNode();

        Currency currency = currencyRepository.findByCurrency(deleteCurrencyRequest.getCurrency_id());

        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User with such id not found!"));

        if (usersCurrenciesRepository.getUsersCurrenciesByUserIsAndCurrency(user, currency).isPresent()) {
            UsersCurrencies userCurrency = usersCurrenciesRepository.getUsersCurrenciesByUserIsAndCurrency(user, currency).orElseThrow(() -> new UsernameNotFoundException("Query not found."));
            usersCurrenciesRepository.delete(userCurrency);
            ((ObjectNode) node).put("status", "success");
            return node;
        } else {
            ((ObjectNode) node).put("status", "abort");
            ((ObjectNode) node).put("issue", "this user dont have this currency");
            return node;
        }
    }

}
