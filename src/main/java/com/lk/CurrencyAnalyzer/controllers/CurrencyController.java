package com.lk.CurrencyAnalyzer.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.text.MessageFormat;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/currency")
public class CurrencyController {

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

}
