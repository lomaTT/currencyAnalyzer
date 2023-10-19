package com.lk.CurrencyAnalyzer.repositories;

import com.lk.CurrencyAnalyzer.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String username);
    boolean existsUserByUserName(String username);
    boolean existsUserByEmail(String email);
}
