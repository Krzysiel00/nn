package com.nn.example.core;

import com.nn.example.core.exception.AccountNotFoundException;
import com.nn.example.core.exception.CurrencyExchangeException;
import com.nn.example.core.exception.InsufficientFundsException;
import com.nn.example.core.models.BankAccount;
import com.nn.example.delivery.models.Currency;
import com.nn.example.external.repository.account.BankAccountRepository;
import com.nn.example.external.repository.currency.rate.ExchangeRateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;

@AllArgsConstructor
@Service
public class ExchangeCurrencyService {

    private final BankAccountRepository bankAccountRepository;
    private final ExchangeRateRepository exchangeRateRepository;

    public ExchangeCurrency exchangeCurrency(ExchangeCurrency exchangeCurrency) {
        BankAccount sourceAccount = bankAccountRepository.findBankAccountByIdNumber(exchangeCurrency.getSourceAccountIdNumber())
                .orElseThrow(() -> new AccountNotFoundException(String.format("account with number:%s not found",exchangeCurrency.getSourceAccountIdNumber())));
        BankAccount targetAccount = bankAccountRepository.findBankAccountByIdNumber(exchangeCurrency.getTargetAccountIdNumber()).
                orElseThrow(() -> new AccountNotFoundException(String.format("account with number:%s not found",exchangeCurrency.getTargetAccountIdNumber())));

        if (sourceAccount.getBalance().compareTo(exchangeCurrency.getAmount()) < 0) {
            throw new InsufficientFundsException("Insufficient funds in the source account.");
        }

        BigDecimal exchangedAmount = performCurrencyExchange(Currency.valueOf(sourceAccount.getCurrency()), exchangeCurrency.getTargetCurrency(), exchangeCurrency.getAmount());

        sourceAccount.setBalance(sourceAccount.getBalance().subtract(exchangeCurrency.getAmount()));
        targetAccount.setBalance(targetAccount.getBalance().add(exchangedAmount));

        bankAccountRepository.saveBankAccount(sourceAccount);
        bankAccountRepository.saveBankAccount(targetAccount);
        return ExchangeCurrency.builder()
                .sourceAccountIdNumber(exchangeCurrency.getSourceAccountIdNumber())
                .targetAccountIdNumber(exchangeCurrency.getTargetAccountIdNumber())
                .amount(exchangeCurrency.getAmount())
                .build();
    }

    private BigDecimal performCurrencyExchange(Currency sourceCurrency, Currency targetCurrency, BigDecimal amount) {
        var rate = exchangeRateRepository.getExchangeRate();
        if (sourceCurrency == Currency.USD && targetCurrency == Currency.PLN) {
            return amount.multiply(rate);
        } else if (sourceCurrency == Currency.PLN && targetCurrency == Currency.USD) {
            return amount.divide(rate, MathContext.DECIMAL32);
        } else throw new CurrencyExchangeException(String.format("Error while exchange currency from:%s to:%s, unknown currency",sourceCurrency,targetCurrency));
    }
}
