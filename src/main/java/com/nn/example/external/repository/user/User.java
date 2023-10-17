package com.nn.example.external.repository.user;



import com.nn.example.external.repository.account.BankAccount;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Builder
@Data
@Getter
@Setter
@Entity(name = "users")
@AllArgsConstructor
@NoArgsConstructor
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private Long cif;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<BankAccount> bankAccounts;
}