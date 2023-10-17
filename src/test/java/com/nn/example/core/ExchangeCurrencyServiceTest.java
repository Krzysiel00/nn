package com.nn.example.core;

import com.nn.example.core.exception.AccountNotFoundException;
import com.nn.example.core.exception.InsufficientFundsException;
import com.nn.example.core.models.BankAccount;
import com.nn.example.delivery.models.Currency;
import com.nn.example.external.repository.account.BankAccountRepository;
import com.nn.example.external.repository.currency.rate.ExchangeRateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExchangeCurrencyServiceTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    ExchangeRateRepository exchangeRateRepository;

    @InjectMocks
    private ExchangeCurrencyService exchangeCurrencyService;

    @Test
    void testExchangeCurrencyFromUSDToPLN() {
        //given
        BankAccount sourceAccount = BankAccount.builder()
                .currency("USD")
                .balance(new BigDecimal("1000.00"))
                .accountId("123456789")
                .build();

        BankAccount targetAccount = BankAccount.builder()
                .currency("PLN")
                .balance(new BigDecimal("2000.00"))
                .accountId("123456789")
                .build();

        ExchangeCurrency exchangeCurrency = ExchangeCurrency.builder()
                .sourceAccountIdNumber("123456789")
                .targetAccountIdNumber("987654321")
                .targetCurrency(Currency.PLN)
                .amount(new BigDecimal("100.00"))
                .accountId(1L)
                .build();
        when(bankAccountRepository.findBankAccountByIdNumber("123456789")).thenReturn(Optional.of(sourceAccount));
        when(bankAccountRepository.findBankAccountByIdNumber("987654321")).thenReturn(Optional.of(targetAccount));
        when(exchangeRateRepository.getExchangeRate()).thenReturn(new BigDecimal("4.2505"));

        //when
        exchangeCurrencyService.exchangeCurrency(exchangeCurrency);

        //then
        assertEquals(new BigDecimal("900.00"), sourceAccount.getBalance());
        assertEquals(new BigDecimal("2425.050000"), targetAccount.getBalance());
    }

    @Test
    void testExchangeCurrencyFromPLNToUSD() {
        // given
        BankAccount sourceAccount = BankAccount.builder()
                .currency("PLN")
                .balance(new BigDecimal("1000.00"))
                .accountId("123456789")
                .build();

        BankAccount targetAccount = BankAccount.builder()
                .currency("USD")
                .balance(new BigDecimal("2000.00"))
                .accountId("123456789")
                .build();

        ExchangeCurrency exchangeCurrency = ExchangeCurrency.builder()
                .sourceAccountIdNumber("123456789")
                .targetAccountIdNumber("987654321")
                .targetCurrency(Currency.USD)
                .amount(new BigDecimal("100.00"))
                .accountId(1L)
                .build();
        when(bankAccountRepository.findBankAccountByIdNumber("123456789")).thenReturn(Optional.of(sourceAccount));
        when(bankAccountRepository.findBankAccountByIdNumber("987654321")).thenReturn(Optional.of(targetAccount));
        when(exchangeRateRepository.getExchangeRate()).thenReturn(new BigDecimal("4.2505"));

        // when
        exchangeCurrencyService.exchangeCurrency(exchangeCurrency);

        //then
        assertEquals(new BigDecimal("900.00"), sourceAccount.getBalance());
        assertEquals(new BigDecimal("2023.52664"), targetAccount.getBalance());
    }


    @Test
    void shouldThrowsAccountNotFoundException() {
        //given
        ExchangeCurrency exchangeCurrency = ExchangeCurrency.builder()
                .sourceAccountIdNumber("123456789")
                .targetAccountIdNumber("987654321")
                .targetCurrency(Currency.PLN)
                .amount(new BigDecimal("100.00"))
                .accountId(1L)
                .build();
        //when
        when(bankAccountRepository.findBankAccountByIdNumber(any())).thenReturn(Optional.empty());
        //then
        assertThrows(AccountNotFoundException.class, () -> exchangeCurrencyService.exchangeCurrency(exchangeCurrency));
    }


    @Test
    public void shouldThrowPerformCurrencyExchangeInsufficientFunds() {
        //given
        BankAccount sourceAccount = BankAccount
                .builder()
                .balance(BigDecimal.TEN)
                .build();

        //when
        when(bankAccountRepository.findBankAccountByIdNumber(any())).thenReturn(Optional.ofNullable(sourceAccount));
        ExchangeCurrency exchangeCurrency = ExchangeCurrency
                .builder()
                .amount(BigDecimal.valueOf(20))
                .build();

        //then
        assertThrows(InsufficientFundsException.class, () -> exchangeCurrencyService.exchangeCurrency(exchangeCurrency));
    }



}
