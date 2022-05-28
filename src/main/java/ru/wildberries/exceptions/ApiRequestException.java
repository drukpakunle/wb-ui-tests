package ru.wildberries.exceptions;

import static org.testng.Assert.fail;

public class ApiRequestException extends RuntimeException {

    public ApiRequestException(Throwable throwable) {
        this(throwable, "");
    }

    public ApiRequestException(Throwable throwable, String message) {
        String errorMessage = String.format("API Request Exception. \nTest Message: %s.\n Exception: %s", message, throwable.getMessage());
        fail(errorMessage);
    }

}
