package com.enigma.hakaton.model.enums;

import java.util.Objects;

public enum Currency {
    RUB(643),
    USD(840),
    EUR(978);

    private final Integer code;

    Currency(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public static Currency getByCode(Integer code) {
        for (Currency value : Currency.values()) {
            if (Objects.equals(value.getCode(), code)) {
                return value;
            }
        }
        throw new IllegalArgumentException();
    }
}
