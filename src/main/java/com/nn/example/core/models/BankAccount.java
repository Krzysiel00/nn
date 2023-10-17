package com.nn.example.core.models;


import lombok.*;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BankAccount {
    private Long id;
    private String accountId;
    private String accountNumber;
    private Long cif;
    private String currency;
    private BigDecimal balance;
}