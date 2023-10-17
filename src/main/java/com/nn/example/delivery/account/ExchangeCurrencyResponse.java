package com.nn.example.delivery.account;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
class ExchangeCurrencyResponse {
    private String sourceIdAccountNumber;
    private String targetIdAccountNumber;
    private String targetCurrency;
    private BigDecimal amount;
    private Long accountId;
}
