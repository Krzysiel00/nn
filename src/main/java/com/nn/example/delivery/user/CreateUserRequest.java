package com.nn.example.delivery.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
class CreateUserRequest {
    private String firstName;
    private String lastName;
    private String address;
}