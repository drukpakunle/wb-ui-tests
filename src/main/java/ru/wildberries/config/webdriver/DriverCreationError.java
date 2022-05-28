package ru.wildberries.config.webdriver;

public class DriverCreationError extends RuntimeException {

    public DriverCreationError(String message) {
        super(message);
    }

    public DriverCreationError(Throwable e) {
        super(e);
    }

    public DriverCreationError(String message, Throwable e) {
        super(message, e);
    }

}

