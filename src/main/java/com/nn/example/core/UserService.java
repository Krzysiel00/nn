package com.nn.example.core;

import com.nn.example.core.exception.AccountNotFoundException;
import com.nn.example.core.exception.UserNotFoundException;
import com.nn.example.core.models.BankAccount;
import com.nn.example.core.models.User;
import com.nn.example.external.repository.account.BankAccountRepository;
import com.nn.example.external.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountNumberGeneratorService accountNumberGeneratorService;

    public User createUser(User user) {
        return userRepository.saveUser(user);
    }

    public BankAccount getUserBankAccount(String accountIdNumber) {
        return bankAccountRepository.findBankAccountByIdNumber(accountIdNumber)
                .orElseThrow(() -> new  AccountNotFoundException("account not found"));
    }

    public BankAccount createBankAccount(BankAccount createBankAccount) {
        createBankAccount.setAccountId(UUID.randomUUID().toString());
        createBankAccount.setAccountNumber(accountNumberGeneratorService.generateAccountNumber());
        userRepository.findByCif(createBankAccount.getCif())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        bankAccountRepository.saveBankAccount(createBankAccount);
        return createBankAccount;
    }


}
