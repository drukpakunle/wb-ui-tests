package ru.wildberries.models;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.wildberries.enums.catalog.Currency;
import ru.wildberries.exceptions.PriceException;

@ToString
@EqualsAndHashCode
public class Price {

    public double amount;
    public Currency currency;
    public double smallAmount;
    public Currency smallCurrency;

    public Price(double amount, Currency currency) {
        this(amount, currency, 0, null);
    }

    public Price(double amount, Currency currency, double smallAmount, Currency smallCurrency) {
        this.amount = amount;
        this.currency = currency;
        this.smallAmount = smallAmount;
        this.smallCurrency = smallCurrency;
    }

    public static Price free() {
        return new Price(0, null, 0, null);
    }

    public Price add(Price addedPrice) {
        boolean isItPossibleToAdd = (this.currency == null || this.currency.equals(addedPrice.currency))
                && (this.smallCurrency == null || this.smallCurrency.equals(addedPrice.smallCurrency));

        if (isItPossibleToAdd) {
            double newAmount = amount + addedPrice.amount;
            double newSmallAmount = smallAmount + addedPrice.smallAmount;

            if (newSmallAmount >= 100) {
                newAmount = newAmount + (int) (newSmallAmount / 100);
                newSmallAmount = newSmallAmount % 100;
            }

            currency = currency != null ? currency : addedPrice.currency;
            smallCurrency = smallCurrency != null ? smallCurrency : addedPrice.smallCurrency;
            return new Price(newAmount, currency, newSmallAmount, smallCurrency);
        } else {
            throw new PriceException(PriceException.DIFFERENT_CURRENCIES_ERROR);
        }
    }

    public Price subtract(Price subtractedPrice) {
        boolean isItPossibleToSubtract = (this.currency == null || this.currency.equals(subtractedPrice.currency))
                && (this.smallCurrency == null || this.smallCurrency.equals(subtractedPrice.smallCurrency));

        if (isItPossibleToSubtract) {
            double newAmount = amount - subtractedPrice.amount;
            double newSmallAmount = smallAmount - subtractedPrice.smallAmount;

            if (newSmallAmount < 0) {
                newAmount--;
                newSmallAmount = 100 + newSmallAmount;
            }

            currency = currency != null ? currency : subtractedPrice.currency;
            smallCurrency = smallCurrency != null ? smallCurrency : subtractedPrice.smallCurrency;
            return new Price(newAmount, currency, newSmallAmount, smallCurrency);
        } else {
            throw new PriceException(PriceException.DIFFERENT_CURRENCIES_ERROR);
        }
    }

    public Price multiply(double factor) {
        double newAmount = amount * factor;
        double newSmallAmount = smallAmount * factor;

        if (newSmallAmount >= 100) {
            newAmount = newAmount + (int) (newSmallAmount / 100);
            newSmallAmount = newSmallAmount % 100;
        }

        return new Price(newAmount, currency, newSmallAmount, smallCurrency);
    }

}
