package com.nn.example.core;

import com.nn.example.delivery.models.Currency;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ExchangeCurrency {
    private String sourceAccountIdNumber;
    private String targetAccountIdNumber;
    private Currency targetCurrency;
    private BigDecimal amount;
    private Long accountId;
}
