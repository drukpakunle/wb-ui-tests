package ru.wildberries.enums.localization;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Locale {
    AM,
    BY,
    KG,
    KZ,
    RU;

    @JsonValue
    private final String name;

    Locale() {
        this.name = this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return this.name;
    }

}