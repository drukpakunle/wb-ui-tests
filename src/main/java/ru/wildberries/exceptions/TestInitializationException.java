package ru.wildberries.exceptions;

import static org.testng.Assert.fail;

public class TestInitializationException extends RuntimeException {

    public TestInitializationException(Throwable throwable) {
        this(throwable, "Test Initialization Failure. \n");
    }

    public TestInitializationException(Throwable throwable, String errorMessage) {
        throwable.printStackTrace();
        fail(errorMessage + throwable.getMessage());
        System.exit(0);
    }

}
