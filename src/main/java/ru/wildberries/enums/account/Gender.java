package ru.wildberries.enums.account;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {

    FEMALE("female"),
    MALE("male");

    @JsonValue
    private final String name;

    Gender(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
