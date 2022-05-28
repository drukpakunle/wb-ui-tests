package ru.wildberries.enums.basket;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DeliveryType {

    SELF("self"),
    COURIER("courier");

    @JsonValue
    private final String name;

    DeliveryType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
