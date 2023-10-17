package com.nn.example.delivery.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
class CreateUserResponse {
    private String firstName;
    private String lastName;
    private String address;
    private Long cif;
}