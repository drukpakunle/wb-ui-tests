package ru.wildberries.enums.catalog;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ClothingType {

    SHOES("shoes"),
    T_SHIRT("t-shirt"),
    PANTS("pants");

    @JsonValue
    private final String name;

    ClothingType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
