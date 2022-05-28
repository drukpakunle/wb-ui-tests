package ru.wildberries.dataprovider.localization;

import ru.wildberries.config.TestConfig;
import ru.wildberries.dataprovider.DtoController;
import ru.wildberries.enums.localization.Locale;

public class TextValuesInstance {

    public static TextValuesDto get() {
        return get(TestConfig.environment.locale);
    }

    public static TextValuesDto get(Locale locale) {
        String textValuesFilePath = getTextValuesPath(locale);
        return new DtoController<>(TextValuesDto.class).getDataFromFile(textValuesFilePath);
    }

    private static String getTextValuesPath(Locale locale) {
        return "localization/" + locale.name().toLowerCase() + ".json";
    }
}