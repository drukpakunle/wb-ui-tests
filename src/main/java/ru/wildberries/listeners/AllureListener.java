package ru.wildberries.listeners;

import io.qameta.allure.listener.TestLifecycleListener;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StatusDetails;
import io.qameta.allure.model.TestResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.Ignore;
import ru.wildberries.annotations.TestAttributes;
import ru.wildberries.config.TestConfig;
import ru.wildberries.enums.localization.Locale;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class AllureListener implements TestLifecycleListener {

    @Override
    public synchronized void beforeTestWrite(TestResult result) {
        Method testMethod;

        try {
            testMethod = getTestMethod(result);
        } catch (Exception | Error e) {
            log.error("AllureListener Exception in getTestMethod() method");
            log.error(e.getMessage());
            e.printStackTrace();
            return;
        }

        try {
            if (testMethod.isAnnotationPresent(Ignore.class)) {
                String cause = String.format("Test mark @Ignore annotation with message: '%s'",
                        testMethod.getAnnotation(Ignore.class).value());

                markTestAsDisabled(result, cause);
                return;
            }
        } catch (Exception | Error e) {
            log.error("AllureListener Exception in markTestAsDisabled() method");
            log.error(e.getMessage());
            e.printStackTrace();
            return;
        }

        try {
            if (!testMethod.isAnnotationPresent(TestAttributes.class)
                    || (testMethod.getAnnotation(TestAttributes.class).includedLocales().length == 0
                    && testMethod.getAnnotation(TestAttributes.class).excludedLocales().length == 0)) {
                return;
            }

            TestAttributes testAttributes = testMethod.getAnnotation(TestAttributes.class);
            List<Locale> allowedLocales = new ArrayList<>(Arrays.asList(Locale.values()));
            List<Locale> includedLocales = Arrays.asList(testAttributes.includedLocales());
            List<Locale> excludedLocales = Arrays.asList(testAttributes.excludedLocales());

            if (includedLocales.size() == 0) {
                allowedLocales.removeAll(excludedLocales);
            } else {
                allowedLocales = includedLocales;
            }

            if (!allowedLocales.contains(TestConfig.environment.locale)) {
                String cause = String.format("Test disabled for '%s' locale", TestConfig.environment.locale);
                markTestAsDisabled(result, cause);
            }
        } catch (Exception | Error e) {
            log.error("AllureListener Exception in check includes and excludes locales method");
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private synchronized void markTestAsDisabled(TestResult result, String cause) {
        StatusDetails statusDetails = new StatusDetails();
        statusDetails.setMessage("Disabled tests");
        statusDetails.setTrace("Cause: " + cause);

        result.setStatus(Status.SKIPPED);
        result.setDescription("Cause: " + cause);
        result.setStatusDetails(statusDetails);
    }

    private Method getTestMethod(TestResult result) throws ClassNotFoundException {
        String testFullName = result.getFullName();
        String className = StringUtils.substringBeforeLast(testFullName, ".");
        String testName = StringUtils.substringAfterLast(testFullName, ".");

        Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(className);

        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.getName().equals(testName))
                .findFirst().get();
    }

}
