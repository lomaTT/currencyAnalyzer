package com.lk.CurrencyAnalyzer.payload.request;

import com.lk.CurrencyAnalyzer.enums.ECurrency;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AddCurrencyRequest {
    private ECurrency currency_id;

    private Double value;

    private Integer user_id;
}
