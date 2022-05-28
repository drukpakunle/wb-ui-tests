package ru.wildberries.enums.catalog;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SizeType {

    RUS("rus"),
    VENDOR("vendor"),
    WAIST_GIRTH("waist girth"),
    HIP_GIRTH("hip girth"),
    FOOT_LENGTH("foot length");

    @JsonValue
    private final String name;

    SizeType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
