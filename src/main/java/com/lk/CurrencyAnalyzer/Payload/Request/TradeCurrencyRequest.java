package com.lk.CurrencyAnalyzer.Payload.Request;


import com.lk.CurrencyAnalyzer.Enums.ECurrency;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TradeCurrencyRequest {
    public ECurrency wantedCurrency;
    public ECurrency existingCurrency;
    public Double value;
    public Double rate;
}
