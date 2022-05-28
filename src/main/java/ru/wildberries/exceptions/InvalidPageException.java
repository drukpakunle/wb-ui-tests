package ru.wildberries.exceptions;

import static org.testng.Assert.fail;

public class InvalidPageException extends RuntimeException {

    public InvalidPageException(String message) {
        super(message);
        fail(message);
    }
}