package com.nn.example.delivery.models;

public enum Currency {

    PLN("PLN"),
    USD("USD");

    private final String currencyCode;
    Currency(String currencyCode) {
        this.currencyCode = currencyCode;
    }


}
