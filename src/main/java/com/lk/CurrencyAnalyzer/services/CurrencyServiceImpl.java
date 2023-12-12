package com.lk.CurrencyAnalyzer.services;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.lk.CurrencyAnalyzer.Enums.ECurrency;
import com.lk.CurrencyAnalyzer.Models.Currency;
import com.lk.CurrencyAnalyzer.Models.Transaction;
import com.lk.CurrencyAnalyzer.Models.User;
import com.lk.CurrencyAnalyzer.Models.UsersCurrencies;
import com.lk.CurrencyAnalyzer.Payload.Request.AddCurrencyRequest;
import com.lk.CurrencyAnalyzer.Payload.Request.DeleteCurrencyRequest;
import com.lk.CurrencyAnalyzer.Payload.Request.TradeCurrencyRequest;
import com.lk.CurrencyAnalyzer.Repositories.CurrencyRepository;
import com.lk.CurrencyAnalyzer.Repositories.TransactionRepository;
import com.lk.CurrencyAnalyzer.Repositories.UserRepository;
import com.lk.CurrencyAnalyzer.Repositories.UsersCurrenciesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.lk.CurrencyAnalyzer.Enums.EOperation.*;
import static com.lk.CurrencyAnalyzer.Enums.EOperation.OPERATION_CONVERT;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private UsersCurrenciesRepository usersCurrenciesRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private static final Logger logger = LoggerFactory.getLogger(CurrencyServiceImpl.class);

    private JsonNode createSuccessJsonNode() {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.createObjectNode();
        ((ObjectNode) node).put("status", "success");
        return node;
    }

    @Override
    public JsonNode getCurrencyInfo(String baseCurrency) {
        String base_url = "https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_HXoFpzn2SbXHwcvdZYd8QMWwV23L1AIsg2CMwB75&base_currency={0}";
        String result = MessageFormat.format(base_url, baseCurrency.toUpperCase());
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

    @Override
    public JsonNode getCurrencyInfo() {
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

    @Override
    public String getListOfCurrencies() {
        ArrayList<String> currenciesArrayToJson = new ArrayList<>();
        ECurrency[] currencies = ECurrency.class.getEnumConstants();
        for (ECurrency currency : currencies) {
            currenciesArrayToJson.add(currency.toString());
        }
        return new Gson().toJson(currenciesArrayToJson);
    }

    @Override
    public JsonNode addCurrencyToCurrenciesList(UserDetailsImpl userDetails, AddCurrencyRequest addCurrencyRequest) {
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

    @Override
    public JsonNode getUsersCurrencies(UserDetailsImpl userDetails) {
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

    @Override
    public JsonNode deleteUserCurrency(UserDetailsImpl userDetails, DeleteCurrencyRequest deleteCurrencyRequest) {
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

    @Override
    public List<Transaction> getLastUserTransactions(UserDetailsImpl userDetails) {
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User with such id not found!"));

        return transactionRepository.getAllByUser(user);
    }

    @Override
    public String tradeCurrency(UserDetailsImpl userDetails, TradeCurrencyRequest tradeCurrencyRequest) {
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