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
        role.setRole(ERole.ROLE_ADMIN);

        ERole retrievedRole = role.getRole();

        assertEquals(ERole.ROLE_ADMIN, retrievedRole);
    }

    @Test
    public void givenId_whenSetId_thenGetIdShouldReturnSameId() {
        Long id = 1L;

        role.setId(id);

        assertEquals(id, role.getId());
    }
}