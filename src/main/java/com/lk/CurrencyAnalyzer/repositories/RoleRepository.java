package com.lk.CurrencyAnalyzer.repositories;

import com.lk.CurrencyAnalyzer.enums.ERole;
import com.lk.CurrencyAnalyzer.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(ERole name);
}
