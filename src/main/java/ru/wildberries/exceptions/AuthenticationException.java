package ru.wildberries.exceptions;

import static org.testng.Assert.fail;

public class AuthenticationException extends RuntimeException {

    public final static String USER_NOT_AUTH_ERROR = "User not authenticated (UserInstance is null)";

    public AuthenticationException() {
        super(USER_NOT_AUTH_ERROR);
        fail(USER_NOT_AUTH_ERROR);
    }
}
