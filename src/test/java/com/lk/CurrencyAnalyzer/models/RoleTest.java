package com.lk.CurrencyAnalyzer.models;

import com.lk.CurrencyAnalyzer.enums.ERole;
import com.lk.CurrencyAnalyzer.models.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoleTest {

    @InjectMocks
    private Role role;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        role = new Role();
    }

    @Test
    public void givenRole_whenSetRole_thenGetRoleShouldReturnSameRole() {
        // Given
        role.setRole(ERole.ROLE_ADMIN);

        // When
        ERole retrievedRole = role.getRole();

        // Then
        assertEquals(ERole.ROLE_ADMIN, retrievedRole);
    }

    @Test
    public void givenId_whenSetId_thenGetIdShouldReturnSameId() {
        // Given
        Long id = 1L;

        // When
        role.setId(id);

        // Then
        assertEquals(id, role.getId());
    }
}