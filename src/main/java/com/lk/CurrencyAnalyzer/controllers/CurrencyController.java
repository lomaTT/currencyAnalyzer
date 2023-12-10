package com.lk.CurrencyAnalyzer.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.lk.CurrencyAnalyzer.enums.ECurrency;
import com.lk.CurrencyAnalyzer.models.Currency;
import com.lk.CurrencyAnalyzer.models.Transaction;
import com.lk.CurrencyAnalyzer.models.User;
import com.lk.CurrencyAnalyzer.models.UsersCurrencies;
import com.lk.CurrencyAnalyzer.payload.request.AddCurrencyRequest;
import com.lk.CurrencyAnalyzer.payload.request.DeleteCurrencyRequest;
import com.lk.CurrencyAnalyzer.payload.request.TradeCurrencyRequest;
import com.lk.CurrencyAnalyzer.repositories.CurrencyRepository;
import com.lk.CurrencyAnalyzer.repositories.TransactionRepository;
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

import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.lk.CurrencyAnalyzer.enums.EOperation.*;

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

    private static final Logger logger = LoggerFactory.getLogger(CurrencyController.class);

    @GetMapping("/get/{base_currency}")
    private JsonNode getCurrencyInfo(@PathVariable String base_currency) {
        String base_url = "https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_HXoFpzn2SbXHwcvdZYd8QMWwV23L1AIsg2CMwB75&base_currency={0}";
        String result = MessageFormat.format(base_url, base_currency.toUpperCase());
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            URL url = new URL(result);
            JsonNode root = objectMapper.readTree(url);
            ((ObjectNode) root.get("data")).put("STATUS", "200");

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

            for (JsonNode node : root.get("data")) {
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
        for (ECurrency currency : currencies) {
            currenciesArrayToJson.add(currency.toString());
        }
        return new Gson().toJson(currenciesArrayToJson);
    }

    @PostMapping("/add-currency-to-currencies-list")
    private JsonNode addCurrencyToCurrenciesList(@Valid @RequestBody AddCurrencyRequest addCurrencyRequest) {
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

        Date now = new Date();

        Transaction transaction = new Transaction(now, user, currency, null, addCurrencyRequest.getValue(), OPERATION_ADD);
        transactionRepository.save(transaction);
        logger.info("User with id " + userDetails.getId() + " made new transaction "
                + OPERATION_ADD + " with value: " + addCurrencyRequest.getValue()
                + ", currency: " + currency.getCurrency().toString());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.createObjectNode();
        ((ObjectNode) node).put("status", "success");

        return node;
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

        Date now = new Date();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.createObjectNode();

        Currency currency = currencyRepository.findByCurrency(deleteCurrencyRequest.getCurrency_id());

        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User with such id not found!"));

        if (usersCurrenciesRepository.getUsersCurrenciesByUserIsAndCurrency(user, currency).isPresent()) {
            UsersCurrencies userCurrency = usersCurrenciesRepository.getUsersCurrenciesByUserIsAndCurrency(user, currency).orElseThrow(() -> new UsernameNotFoundException("Query not found."));
            usersCurrenciesRepository.delete(userCurrency);

            Transaction transaction = new Transaction(now, user, currency, null, userCurrency.getValue(), OPERATION_DELETE);
            transactionRepository.save(transaction);

            logger.info("User with id " + userDetails.getId() + " made new transaction "
                    + OPERATION_DELETE + " with value: " + userCurrency.getValue()
                    + ", currency: " + currency.getCurrency().toString());

            ((ObjectNode) node).put("status", "success");
            return node;
        } else {
            ((ObjectNode) node).put("status", "abort");
            ((ObjectNode) node).put("issue", "this user dont have this currency");
            return node;
        }
    }

    @GetMapping("/get-last-user-transactions")
    private List<Transaction> getLastUserTransaction() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User with such id not found!"));

        return transactionRepository.getAllByUser(user);
    }

    @PostMapping("/trade-currency")
    private String tradeCurrency(@Valid @RequestBody TradeCurrencyRequest tradeCurrencyRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        Date now = new Date();

        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User with such id not found!"));

        Currency existingCurrency = currencyRepository.findByCurrency(tradeCurrencyRequest.getExistingCurrency());
        Currency wantedCurrency = currencyRepository.findByCurrency(tradeCurrencyRequest.getWantedCurrency());

        if (usersCurrenciesRepository.getUsersCurrenciesByUserIsAndCurrency(user, existingCurrency).isPresent()) {
            UsersCurrencies userCurrency = usersCurrenciesRepository.getUsersCurrenciesByUserIsAndCurrency(user, existingCurrency).orElseThrow(() -> new UsernameNotFoundException("Query not found."));

            if (userCurrency.getValue() < tradeCurrencyRequest.getValue()) {
                return "no such amount of value";
            }

            if (userCurrency.getValue() - tradeCurrencyRequest.getValue() != 0) {
                userCurrency.setValue(userCurrency.getValue() - tradeCurrencyRequest.getValue());
                usersCurrenciesRepository.save(userCurrency);
            } else {
                usersCurrenciesRepository.delete(userCurrency);
            }

        } else {
            return "user dont have such currency";
        }

        if (usersCurrenciesRepository.getUsersCurrenciesByUserIsAndCurrency(user, wantedCurrency).isPresent()) {
            UsersCurrencies userCurrency = usersCurrenciesRepository.getUsersCurrenciesByUserIsAndCurrency(user, wantedCurrency).orElseThrow(() -> new UsernameNotFoundException("Query not found."));
            userCurrency.setValue(userCurrency.getValue() + Double.parseDouble((decimalFormat.format(tradeCurrencyRequest.getValue() * tradeCurrencyRequest.getRate())).replaceAll(",", ".")));
            usersCurrenciesRepository.save(userCurrency);
        } else {
            UsersCurrencies usersCurrencies = new UsersCurrencies(
                    user, wantedCurrency, Double.parseDouble((decimalFormat.format(tradeCurrencyRequest.getValue() * tradeCurrencyRequest.getRate())).replaceAll(",", "."))
            );
            usersCurrenciesRepository.save(usersCurrencies);
        }

        Transaction transaction = new Transaction(now, user, existingCurrency, wantedCurrency, tradeCurrencyRequest.getValue(), OPERATION_CONVERT);
        transactionRepository.save(transaction);

        logger.info("User with id " + userDetails.getId() + " made new transaction "
                + OPERATION_CONVERT + " from currency: " + tradeCurrencyRequest.getExistingCurrency().toString()
                + " to currency: " + tradeCurrencyRequest.getWantedCurrency().toString()
                + ", value: " + tradeCurrencyRequest.getValue());

        return "";
    }

}
