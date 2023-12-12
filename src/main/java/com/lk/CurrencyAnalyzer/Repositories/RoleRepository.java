package com.lk.CurrencyAnalyzer.Repositories;

import com.lk.CurrencyAnalyzer.Enums.ERole;
import com.lk.CurrencyAnalyzer.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(ERole name);
}
