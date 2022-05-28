package ru.wildberries.enums.localization;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Letters {

    CYRILLIC("cyrillic"),
    LATIN("latin"),
    MIXED("mixed");

    @JsonValue
    private final String name;

    Letters(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
