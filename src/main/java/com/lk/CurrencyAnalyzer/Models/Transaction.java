package com.lk.CurrencyAnalyzer.Models;

import com.lk.CurrencyAnalyzer.Enums.EOperation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat
    private Date transaction_time;

    @ManyToOne
    private User user;

    @ManyToOne
    private Currency currency;

    @ManyToOne
    private Currency received_currency;

    private Double value;

    @Enumerated(EnumType.STRING)
    private EOperation operation;

    public Currency getCurrencyEnum() {
        return this.currency;
    }

    public Transaction(Date transaction_time, User user, Currency currency, Currency received_currency, Double value, EOperation operation) {
        this.transaction_time = transaction_time;
        this.user = user;
        this.currency = currency;
        this.received_currency = received_currency;
        this.value = value;
        this.operation = operation;
    }

    public Transaction() {}
}
