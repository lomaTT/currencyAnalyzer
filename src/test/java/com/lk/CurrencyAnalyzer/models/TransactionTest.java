package com.lk.CurrencyAnalyzer.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TransactionTest {

    @InjectMocks
    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        transaction = new Transaction();
    }

    @Test
    public void givenTransactionTime_whenSetTransactionTime_thenGetTransactionTimeShouldReturnSameTime() {
        // Given
        Date currentTime = new Date();

        // When
        transaction.setTransaction_time(currentTime);
        Date retrievedTime = transaction.getTransaction_time();

        // Then
        assertEquals(currentTime, retrievedTime);
    }

    @Test
    public void givenUser_whenSetUser_thenGetUserShouldReturnSameUser() {
        // Given
        User user = new User();

        // When
        transaction.setUser(user);
        User retrievedUser = transaction.getUser();

        // Then
        assertNotNull(retrievedUser);
        assertEquals(user, retrievedUser);
    }
}