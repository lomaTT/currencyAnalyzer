package com.lk.CurrencyAnalyzer.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @InjectMocks
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new User();
    }

    @Test
    public void givenUsername_whenSetUsername_thenGetUsernameShouldReturnSameUsername() {
        // Given
        String username = "testUser";

        // When
        user.setUsername(username);
        String retrievedUsername = user.getUsername();

        // Then
        assertEquals(username, retrievedUsername);
    }

    @Test
    public void givenEmail_whenSetEmail_thenGetEmailShouldReturnSameEmail() {
        // Given
        String email = "test@example.com";

        // When
        user.setEmail(email);
        String retrievedEmail = user.getEmail();

        // Then
        assertEquals(email, retrievedEmail);
    }

    @Test
    public void givenPassword_whenSetPassword_thenGetPasswordShouldReturnSamePassword() {
        // Given
        String password = "password123";

        // When
        user.setPassword(password);
        String retrievedPassword = user.getPassword();

        // Then
        assertEquals(password, retrievedPassword);
    }

    @Test
    public void givenRole_whenSetRoles_thenGetRolesShouldReturnSameRoles() {
        // Given
        Role role = new Role();
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        // When
        user.setRoles(roles);
        Set<Role> retrievedRoles = user.getRoles();

        // Then
        assertNotNull(retrievedRoles);
        assertEquals(1, retrievedRoles.size());
        assertTrue(retrievedRoles.contains(role));
    }
}