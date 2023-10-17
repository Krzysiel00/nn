package com.nn.example.delivery.user;


import lombok.*;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
class CreateBankAccountRequest {
    private Long cif;
    private String currency;
    private BigDecimal balance;
}