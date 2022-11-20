package com.enigma.hakaton.model.enums;

public enum Role {
    ADMIN,
    USER;

    @Override
    public String toString() {
        return "{" + "\n" +
                "role: " + this.name() + "\n" +
                "}";
    }
}
