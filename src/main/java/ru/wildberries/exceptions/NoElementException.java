package ru.wildberries.exceptions;

import static org.testng.Assert.fail;

public class NoElementException extends RuntimeException {

    public NoElementException(String message) {
        super(message);
        fail(message);
    }
}