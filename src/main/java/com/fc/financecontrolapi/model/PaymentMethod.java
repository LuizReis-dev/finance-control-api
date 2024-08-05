package com.fc.financecontrolapi.model;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

public enum PaymentMethod {
    CREDITCARD, DEBITCARD, PIX, CASH;

    private static final Set<String> VALID_PAYMENT_METHODS = EnumSet.allOf(PaymentMethod.class)
            .stream()
            .map(Enum::name)
            .collect(Collectors.toSet());

    public static boolean isValid(String value) {
        return VALID_PAYMENT_METHODS.contains(value.toUpperCase());
    }

}
