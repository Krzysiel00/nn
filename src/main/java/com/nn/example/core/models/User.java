package com.nn.example.core.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class User {
    private Long cif;
    private String firstName;
    private String lastName;
    private String address;
}
