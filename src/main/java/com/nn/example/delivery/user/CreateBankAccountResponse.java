package com.nn.example.delivery.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
class CreateBankAccountResponse {
    private Long cif;
    private String currency;
    private BigDecimal balance;
    private String accountIdNumber;
    private String accountNumber;
}
