package com.nn.example.core;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AccountNumberGeneratorService {
    private static final int ACCOUNT_NUMBER_LENGTH = 10;

    public String generateAccountNumber() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < ACCOUNT_NUMBER_LENGTH; i++) {
            int digit = random.nextInt(10);
            stringBuilder.append(digit);
        }
        return stringBuilder.toString();
    }

}

