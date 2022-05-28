package ru.wildberries.utils.parsers;

import ru.wildberries.enums.catalog.Currency;
import ru.wildberries.enums.localization.Locale;
import ru.wildberries.models.Price;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PriceParser {

    private final String pricePattern;

    public PriceParser(Locale locale) {
        switch (locale) {
            case RU:
                pricePattern = "([\\d\\s\\u00A0]+)[\\s\\u00A0]([руб,₽]).?";
                break;
            case BY:
                pricePattern = "([\\d\\s\\u00A0]+)[\\s\\u00A0](р). ([\\d]{0,2}) (к).";
                break;
            case AM:
                pricePattern = "([\\d\\s\\u00A0]+)[\\s\\u00A0](драм)";
                break;
            case KG:
                pricePattern = "([\\d\\s\\u00A0]+)[\\s\\u00A0](сом)";
                break;
            case KZ:
                pricePattern = "([\\d\\s\\u00A0]+)[\\s\\u00A0](тг).?";
                break;
            default:
                throw new IllegalArgumentException("No price pattern for locale: " + locale);
        }
    }

    public Price parse(String priceAsText) {
        Pattern pattern = Pattern.compile(pricePattern);
        Matcher matcher = pattern.matcher(priceAsText);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Exception while parse text: '" + priceAsText + "'");
        }

        double amount = Double.parseDouble(matcher.group(1).replaceAll("[\\u00A0\\s]", ""));
        Currency currency = Currency.get(matcher.group(2));
        double smallAmount = matcher.groupCount() > 2 ? Double.parseDouble(matcher.group(3)) : 0d;
        Currency smallCurrency = matcher.groupCount() > 3 ? Currency.get(matcher.group(4)) : null;

        return new Price(amount, currency, smallAmount, smallCurrency);
    }

}
