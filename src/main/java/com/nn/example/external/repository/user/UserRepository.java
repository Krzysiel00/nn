package com.nn.example.external.repository.user;

import com.nn.example.core.models.User;

import java.util.Optional;

public interface UserRepository {

    public User saveUser(User user);
    public Optional<User> findByCif(Long cif);

}
