package com.nn.example.external.repository.user;

import com.nn.example.core.models.User;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@AllArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final long MAX_CIF_VALUE = 9999999999L;
    private final JpaUserRepository jpaUserRepository;

    @Override
    public User saveUser(User user) {
        jpaUserRepository.save(com.nn.example.external.repository.user.User.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .cif(generateCIF())
                .address(user.getAddress())
                .build());
        return user;
    }

    @Override
    public Optional<User> findByCif(Long cif) {
        return jpaUserRepository.findByCif(cif)
                .map(user -> User.builder()
                        .cif(user.getCif())
                        .address(user.getAddress())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .build());

    }

    private Long generateCIF() {
        return ThreadLocalRandom.current().nextLong(MAX_CIF_VALUE);
    }

}
