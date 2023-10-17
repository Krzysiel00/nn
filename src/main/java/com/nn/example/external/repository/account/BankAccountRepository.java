package com.nn.example.external.repository.account;

import com.nn.example.core.models.BankAccount;

import java.util.Optional;

public interface BankAccountRepository {

    void saveBankAccount(BankAccount createBankAccount);

    Optional<BankAccount> findBankAccountByIdNumber(String idNumber);
}
