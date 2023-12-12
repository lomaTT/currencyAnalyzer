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
        Date currentTime = new Date();

        transaction.setTransaction_time(currentTime);
        Date retrievedTime = transaction.getTransaction_time();

        assertEquals(currentTime, retrievedTime);
    }

    @Test
    public void givenUser_whenSetUser_thenGetUserShouldReturnSameUser() {
        User user = new User();

        transaction.setUser(user);
        User retrievedUser = transaction.getUser();

        assertNotNull(retrievedUser);
        assertEquals(user, retrievedUser);
    }
}