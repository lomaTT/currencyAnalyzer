package com.lk.CurrencyAnalyzer.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users_currencies")
public class UsersCurrencies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Currency currency;

    private Long value;

    public Currency getCurrencyEnum() {
        return this.currency;
    }

    public UsersCurrencies(User user, Currency currency, Long value) {
        this.user = user;
        this.currency = currency;
        this.value = value;
    }

    public UsersCurrencies() {}
}
