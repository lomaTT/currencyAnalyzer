package com.lk.CurrencyAnalyzer.Payload.Request;

import com.lk.CurrencyAnalyzer.Enums.ECurrency;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AddCurrencyRequest {
    private ECurrency currency_id;

    private Double value;

    private Integer user_id;
}
