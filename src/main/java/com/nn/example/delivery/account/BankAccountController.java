package com.nn.example.delivery.account;

import com.nn.example.core.ExchangeCurrency;
import com.nn.example.core.ExchangeCurrencyService;
import com.nn.example.delivery.models.Currency;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@RequestMapping("/bank-accounts")
class BankAccountController {

    private final ExchangeCurrencyService exchangeCurrencyService;

    @PostMapping("/exchange")
    public ExchangeCurrencyResponse exchangeCurrency(@RequestBody ExchangeCurrencyRequest exchangeCurrencyRequest) {
        var exchangeCurrency = this.exchangeCurrencyService.exchangeCurrency(ExchangeCurrency
                .builder()
                .targetAccountIdNumber(exchangeCurrencyRequest.getTargetIdAccountNumber())
                .targetCurrency(Currency.valueOf(exchangeCurrencyRequest.getTargetCurrency()))
                .amount(exchangeCurrencyRequest.getAmount())
                .sourceAccountIdNumber(exchangeCurrencyRequest.getSourceIdAccountNumber())
                .build());

         return ExchangeCurrencyResponse.builder()
                .targetCurrency(exchangeCurrencyRequest.getTargetCurrency())
                .targetIdAccountNumber(exchangeCurrencyRequest.getTargetIdAccountNumber())
                .sourceIdAccountNumber(exchangeCurrency.getSourceAccountIdNumber())
                .amount(exchangeCurrencyRequest.getAmount())
                .build();
    }
}