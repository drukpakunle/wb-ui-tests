package ru.wildberries.listeners;

import lombok.extern.slf4j.Slf4j;
import org.testng.ITestListener;
import org.testng.ITestResult;

@Slf4j
public class BaseTestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult testResult) {
        String methodName = getTestMethodName(testResult);
        log.info("Test {} START", methodName);
    }

    @Override
    public void onTestFailure(ITestResult testResult) {
        String errorMessage = "Unknown Error";

        try {
            errorMessage = testResult.getThrowable().getMessage();
        } finally {
            String methodName = getTestMethodName(testResult);
            log.error("TEST {} FAILURE", methodName);
            log.error("ERROR MESSAGE: {}", errorMessage);
        }
    }

    @Override
    public void onTestSkipped(ITestResult testResult) {
        String methodName = getTestMethodName(testResult);
        log.info("TEST {} SKIPPED. ", methodName);
    }

    @Override
    public void onTestSuccess(ITestResult testResult) {
        String methodName = getTestMethodName(testResult);
        log.info("TEST {} SUCCESS", methodName);
    }

    private String getTestMethodName(ITestResult testResult) {
        try {
            return testResult.getMethod().getMethodName();
        } catch (Exception e) {
            return null;
        }
    }

}