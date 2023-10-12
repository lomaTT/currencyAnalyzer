package com.lk.CurrencyAnalyzer.repositories;

import com.lk.CurrencyAnalyzer.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

}
