package ru.wildberries.enums.catalog;

import java.util.Arrays;
import java.util.List;

public enum Currency {

    RUB("руб", "rub", "₽"),
    BY_RUB("р"),
    BY_KOP("к"),
    TG("тг"),
    DRAM("драм"),
    SOM("сом");

    private final String name;
    private final List<String> aliases;

    /**
     * Aliases allows to specify all possible spellings, given the incorrect encoding
     * of characters whose graphic style is the same as in another Locale.
     * <p>
     * For example, the letter 'p' of the Cyrillic alphabet and the letter 'p' of the Latin alphabet
     * </p>
     *
     * @param aliases String[]
     */
    Currency(final String... aliases) {
        this.name = aliases[0];
        this.aliases = List.of(aliases);
    }

    public static Currency get(String value) {
        return Arrays.stream(values())
                .filter(currency -> currency.aliases.contains(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No Currency with name: " + value));
    }

    @Override
    public String toString() {
        return this.name;
    }

}
