package com.nn.example.delivery.user;

import com.nn.example.core.models.BankAccount;
import com.nn.example.core.models.User;
import com.nn.example.core.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
class UserController {

    private final UserService userService;

    @PostMapping("/registration")
    public CreateUserResponse createUser(@RequestBody CreateUserRequest user) {
        var newUser = userService.createUser(User
                .builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .address(user.getAddress())
                .build());
        return CreateUserResponse
                .builder()
                .firstName(newUser.getFirstName())
                .lastName(newUser.getLastName())
                .cif(newUser.getCif())
                .address(newUser.getAddress())
                .build();
    }


    @PostMapping("/account")
    public CreateBankAccountResponse createBankAccount(@RequestBody CreateBankAccountRequest createBankAccountRequest) {
        var newAccount = userService.createBankAccount(BankAccount
                .builder()
                .cif(createBankAccountRequest.getCif())
                .balance(createBankAccountRequest.getBalance())
                .currency(createBankAccountRequest.getCurrency()).
                build());
        return CreateBankAccountResponse
                .builder()
                .accountNumber(newAccount.getAccountNumber())
                .accountIdNumber(newAccount.getAccountId())
                .balance(newAccount.getBalance())
                .cif(newAccount.getCif())
                .currency(newAccount.getCurrency())
                .build();
    }

    @GetMapping("/account/{accountIdNumber}")
    public BankAccountResponse getAccountInformation(@PathVariable String accountIdNumber) {
        var bankAccount = userService.getUserBankAccount(accountIdNumber);
        return BankAccountResponse.builder()
                .accountId(bankAccount.getAccountId())
                .accountNumber(bankAccount.getAccountNumber())
                .balance(bankAccount.getBalance())
                .currency(bankAccount.getCurrency())
                .cif(bankAccount.getCif())
                .build();
    }
}