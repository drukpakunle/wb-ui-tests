package ru.wildberries.utils;

import java.util.Arrays;

public enum TestResult {

    UNKNOWN(0),
    CREATED(-1),
    SUCCESS(1),
    FAILURE(2),
    SKIP(3),
    SUCCESS_PERCENTAGE_FAILURE(4),
    STARTED(16);

    private final int code;

    TestResult(final int code) {
        this.code = code;
    }

    public static TestResult get(int testCode) {
        return Arrays.stream(values())
                .filter(code -> code.getValue() == testCode)
                .findFirst()
                .orElse(UNKNOWN);
    }

    public int getValue() {
        return code;
    }
}
