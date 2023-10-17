package com.nn.example.delivery.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Builder
@Getter
@Setter
@AllArgsConstructor
class BankAccountResponse {
    private String accountId;
    private String accountNumber;
    private Long cif;
    private String currency;
    private BigDecimal balance;
}
