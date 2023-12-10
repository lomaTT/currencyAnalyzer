package com.lk.CurrencyAnalyzer.payload.request;


import com.lk.CurrencyAnalyzer.enums.ECurrency;
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
