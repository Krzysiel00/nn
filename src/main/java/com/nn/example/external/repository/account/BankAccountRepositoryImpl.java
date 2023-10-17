package com.nn.example.external.repository.account;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import com.nn.example.core.models.BankAccount;
import java.util.Optional;

@AllArgsConstructor
@Repository
class BankAccountRepositoryImpl implements BankAccountRepository {

    private final JpaBankAccountRepository jpaBankAccountRepository;

    @Override
    public void saveBankAccount(BankAccount bankAccount) {
        jpaBankAccountRepository.save(com.nn.example.external.repository.account.BankAccount
                .builder()
                .id(bankAccount.getId())
                .idNumber(bankAccount.getAccountId())
                .number(bankAccount.getAccountNumber())
                .balance(bankAccount.getBalance())
                .currency(bankAccount.getCurrency())
                .cif(bankAccount.getCif())
                .build());
    }

    @Override
    public Optional<BankAccount> findBankAccountByIdNumber(String idNumber) {
        return jpaBankAccountRepository.findBankAccountByIdNumber(idNumber)
                .map(bankAccount -> BankAccount
                        .builder()
                        .id(bankAccount.getId())
                        .accountId(bankAccount.getIdNumber())
                        .cif(bankAccount.getCif())
                        .balance(bankAccount.getBalance())
                        .accountNumber(bankAccount.getNumber())
                        .currency(bankAccount.getCurrency())
                        .build());
    }
}
