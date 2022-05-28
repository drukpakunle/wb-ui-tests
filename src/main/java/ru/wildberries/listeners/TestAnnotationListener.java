package ru.wildberries.listeners;

import lombok.extern.slf4j.Slf4j;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;
import org.testng.annotations.Ignore;
import ru.wildberries.annotations.TestAttributes;
import ru.wildberries.config.TestConfig;
import ru.wildberries.enums.localization.Locale;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class TestAnnotationListener implements IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        log.info("Transform annotation method: {} BEGIN", testMethod);

        try {
            if (testMethod.isAnnotationPresent(Ignore.class)) {
                log.info("Ignore annotation is Present in method: {}", testMethod);
                testMethod.setAccessible(true);
                annotation.setEnabled(false);
                return;
            }

            if (!testMethod.isAnnotationPresent(TestAttributes.class)) {
                log.error("Method '{}' NOT mark TestAttributes annotation", testMethod);
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
                log.info("Test: '{}' disabled for these locales: {}", annotation.getDescription(), excludedLocales);
                annotation.setEnabled(false);
            }

            log.info("Test method: {} without restrictions on locales", testMethod);
        } catch (Exception | Error e) {
            log.error(e.getMessage());
            e.printStackTrace();
        } finally {
            log.info("Transform annotation method {} END", testMethod);
        }
    }

}
