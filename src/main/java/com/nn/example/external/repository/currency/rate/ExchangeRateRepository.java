package com.nn.example.external.repository.currency.rate;

import java.math.BigDecimal;

public interface ExchangeRateRepository {
    BigDecimal getExchangeRate();
}
