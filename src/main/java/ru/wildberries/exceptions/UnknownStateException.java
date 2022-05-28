package ru.wildberries.exceptions;

import static org.testng.Assert.fail;

public class UnknownStateException extends RuntimeException {

    public UnknownStateException(String message) {
        super(message);
        fail(message);
    }
}