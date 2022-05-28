package ru.wildberries.exceptions;

import static org.testng.Assert.fail;

public class PriceException extends RuntimeException {

    public final static String DIFFERENT_CURRENCIES_ERROR = "Can not calculate prices with different currencies";

    public PriceException(String message) {
        super(message);
        fail(message);
    }

}