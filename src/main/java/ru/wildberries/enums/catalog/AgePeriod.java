package ru.wildberries.enums.catalog;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AgePeriod {

    ADULT("adult"),
    TEENAGER("teenager"),
    CHILD("child");

    @JsonValue
    private final String name;

    AgePeriod(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
