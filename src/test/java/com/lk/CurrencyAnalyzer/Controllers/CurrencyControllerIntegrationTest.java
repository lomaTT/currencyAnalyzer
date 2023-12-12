package com.lk.CurrencyAnalyzer.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lk.CurrencyAnalyzer.Enums.ECurrency;
import com.lk.CurrencyAnalyzer.Payload.Request.AddCurrencyRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class CurrencyControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenBaseCurrency_whenGetCurrencyInfo_thenStatusIs200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/currency/get/USD"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.STATUS").value("200"));
    }

    @Test
    public void givenAddCurrencyRequest_whenAddCurrencyToCurrenciesList_thenStatusIsSuccess() throws Exception {
        AddCurrencyRequest request = new AddCurrencyRequest();
        request.setCurrency_id(ECurrency.CURRENCY_USD);
        request.setValue(100.0);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/currency/add-currency-to-currencies-list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("success"));
    }
}