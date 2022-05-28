package ru.wildberries.exceptions;

import static org.testng.Assert.fail;

public class PageNotOpenException extends RuntimeException {

    public PageNotOpenException(String message) {
        fail(message);
    }

    public PageNotOpenException(String message, Throwable cause) {
        fail(message, cause);
    }

}