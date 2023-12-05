package com.lk.CurrencyAnalyzer.payload.request;

import com.lk.CurrencyAnalyzer.enums.ECurrency;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DeleteCurrencyRequest {
    private ECurrency currency_id;
    private Integer user_id;
}
