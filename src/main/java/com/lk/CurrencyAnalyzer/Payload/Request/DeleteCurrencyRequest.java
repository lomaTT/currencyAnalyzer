package com.lk.CurrencyAnalyzer.Payload.Request;

import com.lk.CurrencyAnalyzer.Enums.ECurrency;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DeleteCurrencyRequest {
    private ECurrency currency_id;
    private Integer user_id;
}
