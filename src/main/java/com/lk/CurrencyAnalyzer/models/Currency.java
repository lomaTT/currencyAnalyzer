package com.lk.CurrencyAnalyzer.models;

import com.lk.CurrencyAnalyzer.enums.ECurrency;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Table(name = "currency")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Enumerated(EnumType.STRING)
    private ECurrency currency;

    public ECurrency getCurrencyName() {
        return this.currency;
    }

}
