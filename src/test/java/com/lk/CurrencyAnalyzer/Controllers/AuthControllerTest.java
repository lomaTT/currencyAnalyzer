package com.lk.CurrencyAnalyzer.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lk.CurrencyAnalyzer.Models.User;
import com.lk.CurrencyAnalyzer.Payload.Request.SignupRequest;
import com.lk.CurrencyAnalyzer.Payload.Response.MessageResponse;
import com.lk.CurrencyAnalyzer.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @Test
    void registerUserIntegrationTest() throws Exception {
        // Create a mock SignupRequest
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("testUser");
        signupRequest.setEmail("test@test.com");
        signupRequest.setPassword("testPassword");

        // Mock the behavior of authService.registerUser
        MessageResponse mockResponse = new MessageResponse("User registered successfully!");
        when(authService.registerUser(any(SignupRequest.class))).thenReturn(mockResponse);

        // Perform the HTTP request and verify the response
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signupRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Convert the response to MessageResponse
        String content = mvcResult.getResponse().getContentAsString();
        MessageResponse actualResponse = objectMapper.readValue(content, MessageResponse.class);

        // Assertions
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(mockResponse, actualResponse);
    }
}